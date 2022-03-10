package com.example.matchatapp.feature_chatting.domain.model

import android.net.Uri

data class User(
    val userId: String,
    val userName: String? = null,
    val userBio: String? = null,
    val userPhone: String,
    val userProfilePic: String? = null
)
