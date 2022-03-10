package com.example.matchatapp.feature_chatting.data.repository

import android.util.Log
import com.example.matchatapp.feature_chatting.domain.repository.CloudMessagesRepo
import com.example.matchatapp.utils.Constants.TAG
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

class CloudMessagesRepoImpl @Inject constructor(
    private val firebaseMessaging: FirebaseMessaging
) : CloudMessagesRepo {
    override fun getDeviceRegistrationToken() {
        firebaseMessaging.token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.i(TAG, "Fetching FCM registration token failed")
                return@OnCompleteListener
            }
            val token = task.result
            Log.i(TAG, "getDeviceRegistrationToken: Token -> $token")
        })
    }
}