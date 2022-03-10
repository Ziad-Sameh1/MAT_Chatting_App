package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.CheckIfChatRoomCreatedBeforeResultListener
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class CheckIfChatRoomIsCreatedBeforeUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(
        firstUserPhoneNumber: String,
        secondUserPhoneNumber: String,
        checkIfChatRoomCreatedBeforeResultListener: CheckIfChatRoomCreatedBeforeResultListener
    ) =
        repo.checkIfChatRoomAvailable(
            firstUserPhoneNumber = firstUserPhoneNumber,
            secondUserPhoneNumber = secondUserPhoneNumber,
            checkIfChatRoomCreatedBeforeResultListener = checkIfChatRoomCreatedBeforeResultListener
        )
}