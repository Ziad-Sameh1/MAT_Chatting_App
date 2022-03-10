package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.AddDataToDatabaseResultListener
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class AddOrUpdateUserDataUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(user: User, resultListener: AddDataToDatabaseResultListener) {
        repo.addOrUpdateUserData(user = user, resultListener)
    }
}