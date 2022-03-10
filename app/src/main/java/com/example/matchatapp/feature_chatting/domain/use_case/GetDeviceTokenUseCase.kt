package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.domain.repository.CloudMessagesRepo
import javax.inject.Inject

class GetDeviceTokenUseCase @Inject constructor(
    private val repo: CloudMessagesRepo
) {
    operator fun invoke() = repo.getDeviceRegistrationToken()
}