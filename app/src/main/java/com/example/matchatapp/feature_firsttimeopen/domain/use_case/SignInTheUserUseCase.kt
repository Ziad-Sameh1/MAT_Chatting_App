package com.example.matchatapp.feature_firsttimeopen.domain.use_case

import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

class SignInTheUserUseCase @Inject constructor(
    private val repository: AuthRepo
) {
    operator fun invoke(smsCode: String){
        repository.signInTheUser(smsCode = smsCode)
    }
}