package com.example.matchatapp.feature_chatting.presentation.chat_list

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchatapp.CurrentOpenedChatRoomId
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_chatting.data.interfaces.CheckIfChatRoomCreatedBeforeResultListener
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.Response
import com.example.matchatapp.feature_chatting.domain.use_case.CheckIfChatRoomIsCreatedBeforeUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.GetUserChatRoomsUseCase
import com.example.matchatapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatListViewModel @Inject constructor(
    private val getUserChatRoomsUseCase: GetUserChatRoomsUseCase,
    private val checkIfChatRoomIsCreatedBeforeUseCase: CheckIfChatRoomIsCreatedBeforeUseCase
) : ViewModel() {

    init {
        viewModelScope.launch {
            delay(1500)
            getChatRooms()
        }
    }

    private val _isLoadingState = mutableStateOf<Boolean>(true)
    val isLoadingState: State<Boolean> = _isLoadingState

    private val _currentChatRoom = mutableStateOf<String>("")
    val currentChatRoom: State<String> = _currentChatRoom

    private val _isErrorState = mutableStateOf<Boolean>(false)
    val isErrorState: State<Boolean> = _isErrorState

    private val _chatRooms = mutableStateOf<List<ChatRoom>>(emptyList())
    val chatRooms: State<List<ChatRoom>> = _chatRooms

    private val _isSettingsDropDownMenuExpanded = mutableStateOf<Boolean>(false)
    val isSettingsDropDownMenuExpanded: State<Boolean> = _isSettingsDropDownMenuExpanded

    private val _isDoneChecking = mutableStateOf<Boolean>(false)
    val isDoneChecking: State<Boolean> = _isDoneChecking

    fun onIsDoneCheckingStateChanges(newValue: Boolean) {
        _isDoneChecking.value = newValue
    }

    fun onSettingsDropDownMenuExpandedStateChanges(newValue: Boolean) {
        _isSettingsDropDownMenuExpanded.value = newValue
    }

    fun getAnotherUserIndex(chatRoom: ChatRoom): Int {
        Log.i(TAG, "getAnotherUserIndex: ${chatRoom.userId}")
        return if (chatRoom.userId[0] == LoggedInUserData.loggedInUserId) {
            1
        } else {
            0
        }
    }

    private fun getChatRooms() {
        Log.i(TAG, "getChatRooms: ${LoggedInUserData.loggedInUserPhoneNumber}")
        getUserChatRoomsUseCase(LoggedInUserData.loggedInUserPhoneNumber).onEach { chatRoom ->
            when (chatRoom) {
                is Response.Success -> {
                    _isLoadingState.value = false
                    _chatRooms.value = chatRoom.data!!

                }
                is Response.Error -> {
                    _isLoadingState.value = false
                    _isErrorState.value = true
                }
                is Response.Loading -> {
                    _isLoadingState.value = true
                }
            }
        }.launchIn(viewModelScope)
    }


    fun checkIfChatRoomCreatedBefore(
        firstUserPhoneNumber: String,
        secondUserPhoneNumber: String
    ) {
        _currentChatRoom.value = ""
        checkIfChatRoomIsCreatedBeforeUseCase(
            firstUserPhoneNumber = firstUserPhoneNumber,
            secondUserPhoneNumber = secondUserPhoneNumber,
            checkIfChatRoomCreatedBeforeResultListener = object :
                CheckIfChatRoomCreatedBeforeResultListener {
                override fun onSuccess(chatRoomId: String) {
                    _currentChatRoom.value = chatRoomId
                    _isDoneChecking.value = true
                }

                override fun onNotFound() {
                    _isDoneChecking.value = true
                }
            }
        )
    }

    fun saveChatRoomId(chatRoomId: String) {
        CurrentOpenedChatRoomId.id = chatRoomId
    }
}