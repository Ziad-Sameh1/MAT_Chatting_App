package com.example.matchatapp.feature_firsttimeopen.domain.repository

import android.app.Activity
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {

    fun doAuth(phoneNumber: String, activity : Activity)
    fun signInTheUser(smsCode: String)
    fun signedInUserData() : FirebaseUser?
    fun signingInStatus() : String
}