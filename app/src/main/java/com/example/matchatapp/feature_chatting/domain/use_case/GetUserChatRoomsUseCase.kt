package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class GetUserChatRoomsUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(userId: String) = repo.getUserChats(userId = userId)
}