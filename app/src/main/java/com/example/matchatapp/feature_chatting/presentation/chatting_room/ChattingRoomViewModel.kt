package com.example.matchatapp.feature_chatting.presentation.chatting_room

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.matchatapp.CurrentOpenedChatRoomId
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_chatting.data.interfaces.AddMessageToChatRoomResultListener
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.model.Response
import com.example.matchatapp.feature_chatting.domain.use_case.AddMessageToChatRoomUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.ReadChatRoomMessagesUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.UpdateLastMessageUseCase
import com.example.matchatapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ChattingRoomViewModel @Inject constructor(
    private val addMessageToChatRoomUseCase: AddMessageToChatRoomUseCase,
    private val readChatRoomMessagesUseCase: ReadChatRoomMessagesUseCase,
    private val updateLastMessageUseCase: UpdateLastMessageUseCase
) : ViewModel() {

    private var _messages = mutableStateListOf<Message>()
    val messages: SnapshotStateList<Message> = _messages

    private val _firstUserPhone = mutableStateOf<String>("")
    val firstUserPhone: State<String> = _firstUserPhone

    private val _secondUserPhone = mutableStateOf<String>("")
    val secondUserPhone: State<String> = _secondUserPhone

    private val _enteredMessageState = mutableStateOf<String>("")
    val enteredMessageState: State<String> = _enteredMessageState

    private val _chattingRoomId = mutableStateOf<String>("")
    val chattingRoomId: State<String> = _chattingRoomId

    init {
        _firstUserPhone.value = LoggedInUserData.loggedInUserId
        _chattingRoomId.value = CurrentOpenedChatRoomId.id
        readMessagesFromDatabase(chatRoomId = chattingRoomId.value)
    }

    fun onEnteredMessageStateChanges(newValue: String) {
        _enteredMessageState.value = newValue
    }

    fun addMessageToDatabase(message: Message, chatRoomId: String) {
        addMessageToChatRoomUseCase(
            chatRoomId = chatRoomId,
            message = message,
            addMessageToChatRoomResultListener = object : AddMessageToChatRoomResultListener {
                override fun onSuccess() {
                    Log.i(TAG, "onSuccess: Message Added Successfully")
                }

                override fun onFailure(error: Throwable) {
                    Log.i(TAG, "onFailure: Message Adding Failed error -> ${error.message}")
                }

            }
        )
    }

    fun updateLastMessage(message: Message, chatRoomId: String) {
        updateLastMessageUseCase(message = message, chatRoomId = chatRoomId)
    }

    private fun readMessagesFromDatabase(chatRoomId: String) {
        readChatRoomMessagesUseCase(
            chatRoomId = chatRoomId
        ).onEach { messageList ->
            when (messageList) {
                is Response.Success -> {
                    _messages.clear()
                    if (messageList.data?.isEmpty() == false) {
                        for (i in 0 until messageList.data.size) {
                            _messages.add(messageList.data[i])
                        }
                    }
                }
                is Response.Error -> {
                    Log.i(TAG, "readMessagesFromDatabase: Error")
                }
                is Response.Loading -> {
                    Log.i(TAG, "readMessagesFromDatabase: Loading")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getAnotherUserIndex(chatRoom: ChatRoom): Int {
        return if (chatRoom.userId[0] == LoggedInUserData.loggedInUserId) {
            1
        } else {
            0
        }
    }
}