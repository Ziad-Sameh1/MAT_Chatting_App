package com.example.matchatapp.feature_firsttimeopen.data.repository

import android.app.Activity
import android.util.Log
import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import com.example.matchatapp.utils.Constants
import com.example.matchatapp.utils.Constants.TAG
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepo {
    private var storedVerificationId = ""
    private var signingInStatus = Constants.NOT_VERIFIED
    override fun doAuth(phoneNumber: String, activity: Activity) {
        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential = credential)
                Log.i(Constants.TAG, "onVerificationCompleted: ")
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Log.i(Constants.TAG, "onVerificationFailed: ")
            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                storedVerificationId = verificationId
            }
        }
        val options = PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS).setCallbacks(callbacks).setActivity(activity).build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    override fun signInTheUser(smsCode: String) {
        if (storedVerificationId != "") {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId, smsCode)
            signInWithPhoneAuthCredential(credential = credential)
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        signingInStatus = Constants.LOADING
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                Log.i(Constants.TAG, "signInWithPhoneAuthCredential: Signed In Successfully")
                signingInStatus = Constants.VERIFIED
                storedVerificationId = ""
            } else {
                // Sign in failed, display a message and update the UI
                if (it.exception is FirebaseAuthInvalidCredentialsException) {
                    signingInStatus = Constants.FAILED
                    Log.i(
                        Constants.TAG,
                        "signInWithPhoneAuthCredential: Verification code is wrong"
                    )
                }
            }
        }
    }

    override fun signedInUserData(): FirebaseUser? {

        return firebaseAuth.currentUser
    }

    override fun signingInStatus(): String {
        return signingInStatus
    }


}