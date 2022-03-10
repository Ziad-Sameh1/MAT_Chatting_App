package com.example.matchatapp.feature_firsttimeopen.domain.use_case

import android.app.Activity
import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(
    private val repository: AuthRepo
) {
    operator fun invoke(phoneNumber: String, activity: Activity) {
        repository.doAuth(phoneNumber = phoneNumber, activity = activity)
    }
}