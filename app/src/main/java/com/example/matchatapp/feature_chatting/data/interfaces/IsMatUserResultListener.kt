package com.example.matchatapp.feature_chatting.data.interfaces

import com.example.matchatapp.feature_chatting.domain.model.User

interface IsMatUserResultListener {
    fun onSuccess(user: User)
    fun onFailure(error: Throwable)
}