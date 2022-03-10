package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.GetUserPersonalInfoResultListener
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class GetUserPersonalDataUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(
        userPhoneNumber: String,
        getUserPersonalInfoResultListener: GetUserPersonalInfoResultListener
    ) =
        repo.getUserPersonalInfo(
            userPhoneNumber = userPhoneNumber,
            getUserPersonalInfoResultListener = getUserPersonalInfoResultListener
        )
}