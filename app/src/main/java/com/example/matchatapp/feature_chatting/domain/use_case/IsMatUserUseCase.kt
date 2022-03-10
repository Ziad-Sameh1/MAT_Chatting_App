package com.example.matchatapp.feature_chatting.domain.use_case

import com.example.matchatapp.feature_chatting.data.interfaces.IsMatUserResultListener
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import javax.inject.Inject

class IsMatUserUseCase @Inject constructor(
    private val repo: DatabaseRepo
) {
    operator fun invoke(userPhoneNumber: String, isMatUserResultListener: IsMatUserResultListener) {
        return repo.isMatUser(
            userPhoneNumber = userPhoneNumber,
            isMatUserResultListener = isMatUserResultListener
        )
    }
}