package com.example.matchatapp.feature_chatting.domain.repository

import com.example.matchatapp.feature_chatting.data.interfaces.*
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.model.Response
import com.example.matchatapp.feature_chatting.domain.model.User
import kotlinx.coroutines.flow.Flow

interface DatabaseRepo {

    fun getUserChats(userId: String): Flow<Response<List<ChatRoom>>>

    fun addOrUpdateUserData(
        user: User,
        addOrUpdateUserResultListener: AddDataToDatabaseResultListener
    )

    fun getUserPersonalInfo(userPhoneNumber: String, getUserPersonalInfoResultListener: GetUserPersonalInfoResultListener)

    fun isMatUser(userPhoneNumber: String, isMatUserResultListener: IsMatUserResultListener)

    fun createChatRoom(
        chatRoom: ChatRoom,
        createChatRoomResultListener: CreateChatRoomResultListener
    )

    fun addMessageToChatRoom(
        chatRoomId: String,
        message: Message,
        addMessageToChatRoom: AddMessageToChatRoomResultListener
    )

    fun readChatRoomMessages(
        chatRoomId: String
    ): Flow<Response<List<Message>>>

    fun checkIfChatRoomAvailable(
        firstUserPhoneNumber: String,
        secondUserPhoneNumber: String,
        checkIfChatRoomCreatedBeforeResultListener: CheckIfChatRoomCreatedBeforeResultListener
    )

    fun updateLastMessage(message: Message, chatRoomId: String)
}