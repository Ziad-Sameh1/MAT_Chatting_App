package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.CreateChatRoomResultListener
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class CreateChatRoomUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(
        chatRoom: ChatRoom,
        createChatRoomResultListener: CreateChatRoomResultListener
    ) = repo.createChatRoom(
        chatRoom = chatRoom,
        createChatRoomResultListener = createChatRoomResultListener
    )
}