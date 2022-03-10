package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.AddMessageToChatRoomResultListener
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class AddMessageToChatRoomUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(
        chatRoomId: String,
        message: Message,
        addMessageToChatRoomResultListener: AddMessageToChatRoomResultListener
    ) = repo.addMessageToChatRoom(
        chatRoomId = chatRoomId,
        message = message,
        addMessageToChatRoom = addMessageToChatRoomResultListener
    )
}