package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.model.Response
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadChatRoomMessagesUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(
        chatRoomId: String
    ): Flow<Response<List<Message>>> =
        repo.readChatRoomMessages(
            chatRoomId = chatRoomId
        )
}