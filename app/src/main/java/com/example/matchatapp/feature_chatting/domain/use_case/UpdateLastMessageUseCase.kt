package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class UpdateLastMessageUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(message: Message, chatRoomId: String) =
        repo.updateLastMessage(message = message, chatRoomId = chatRoomId)
}