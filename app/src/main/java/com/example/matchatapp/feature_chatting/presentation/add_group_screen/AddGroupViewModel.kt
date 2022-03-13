package com.example.matchatapp.feature_chatting.presentation.add_group_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchatapp.CurrentOpenedChatRoomId
import com.example.matchatapp.feature_chatting.data.interfaces.CheckIfChatRoomCreatedBeforeResultListener
import com.example.matchatapp.feature_chatting.data.interfaces.CreateChatRoomResultListener
import com.example.matchatapp.feature_chatting.data.interfaces.IsMatUserResultListener
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.Contact
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.domain.use_case.CheckIfChatRoomIsCreatedBeforeUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.CreateChatRoomUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.GetContactsUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.IsMatUserUseCase
import com.example.matchatapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddGroupViewModel @Inject constructor(
    private val getContactsUseCase: GetContactsUseCase,
    private val isMatUserUseCase: IsMatUserUseCase,
    private val createChatRoomUseCase: CreateChatRoomUseCase,
    private val checkIfChatRoomIsCreatedBeforeUseCase: CheckIfChatRoomIsCreatedBeforeUseCase
) : ViewModel() {


    private var deviceContacts: List<Contact> = emptyList()

    private val _clickedUser = mutableStateOf<User>(User(userId = "", userPhone = ""))
    val clickedUser: State<User> = _clickedUser

    private val _matUsers = mutableStateListOf<User>()
    val matUsers: SnapshotStateList<User> = _matUsers

    private val _isLoadingState = mutableStateOf<Boolean>(false)
    val isLoadingState: State<Boolean> = _isLoadingState

    private val _creatingRoomLoadingState = mutableStateOf<Boolean>(false)
    val creatingRoomLoadingState: State<Boolean> = _creatingRoomLoadingState

    private val _doNavigate = mutableStateOf<Boolean>(false)
    val doNavigate: State<Boolean> = _doNavigate

    private val _currentChatRoom = mutableStateOf<String>("")
    val currentChatRoom: State<String> = _currentChatRoom

    private val _isDoneChecking = mutableStateOf<Boolean>(false)
    val isDoneChecking: State<Boolean> = _isDoneChecking

    private val _onFirstNotFound = mutableStateOf<Boolean>(false)

    private val _onSecondNotFound = mutableStateOf<Boolean>(false)

    private val _onBothNotFound = mutableStateOf<Boolean>(false)
    val onBothNotFound: State<Boolean> = _onBothNotFound

    fun onBothNotFoundStateChanges(onFirstNotFound: Boolean, onSecondNotFound: Boolean) {
        _onBothNotFound.value = onFirstNotFound && onSecondNotFound
        _currentChatRoom.value = ""
    }

    fun onIsDoneCheckingStateChanges(newValue: Boolean) {
        _isDoneChecking.value = newValue
    }

    fun onDoNavigateStateChanges(newValue: Boolean) {
        _doNavigate.value = newValue
    }

    fun getAllContacts() {
        deviceContacts = getContactsUseCase()
    }

    fun onIsLoadingStateChanges(newValue: Boolean) {
        _isLoadingState.value = newValue
    }

    private fun getMatUsers() {
        _matUsers.clear()
        for (i in deviceContacts.indices) {
            // for each device contact check if it is a mat user
            isMatUserUseCase(
                deviceContacts[i].phoneNumber?.filter { !it.isWhitespace() } ?: "",
                isMatUserResultListener = object : IsMatUserResultListener {
                    override fun onSuccess(user: User) {
                        if (user.userId.isNotEmpty()) {
                            _isLoadingState.value = false
                            _matUsers.add(user)
                        }
                    }

                    override fun onFailure(error: Throwable) {
                        _isLoadingState.value = false
                        Log.i(TAG, "onFailure: error -> ${error.message}")
                    }

                })
            // TODO: Handle Case if no one of the contacts uses mat
        }
    }

    fun getContacts() {
        viewModelScope.launch {
            getAllContacts()
            getMatUsers()
            _isLoadingState.value = false
        }
    }

    fun createChatRoom(chatRoom: ChatRoom) {
        createChatRoomUseCase(
            chatRoom = chatRoom,
            createChatRoomResultListener = object : CreateChatRoomResultListener {
                override fun onSuccess() {
                    _creatingRoomLoadingState.value = false
                    _doNavigate.value = true
                    _currentChatRoom.value = chatRoom.chatRoomId ?: ""
                }

                override fun onFailure(error: Throwable) {
                    _creatingRoomLoadingState.value = false
                    _doNavigate.value = false
                }

            })
    }

    fun checkIfChatRoomCreatedBefore(
        firstUserPhoneNumber: String,
        secondUser: User
    ) {
        _clickedUser.value = secondUser
        _currentChatRoom.value = ""
        checkIfChatRoomIsCreatedBeforeUseCase(
            firstUserPhoneNumber = firstUserPhoneNumber,
            secondUserPhoneNumber = secondUser.userPhone,
            checkIfChatRoomCreatedBeforeResultListener = object :
                CheckIfChatRoomCreatedBeforeResultListener {
                override fun onSuccess(chatRoomId: String) {
                    Log.i(TAG, "onSuccess: chatRoomId -> $chatRoomId")
                    _currentChatRoom.value = chatRoomId
                    _isDoneChecking.value = true
                }

                override fun onNotFound() {
                    Log.i(TAG, "onNotFound: ----------------------")
                    _isDoneChecking.value = true
                }
            }
        )
    }

    fun saveChatRoomId(chatRoomId: String) {
        CurrentOpenedChatRoomId.id = chatRoomId
    }
}