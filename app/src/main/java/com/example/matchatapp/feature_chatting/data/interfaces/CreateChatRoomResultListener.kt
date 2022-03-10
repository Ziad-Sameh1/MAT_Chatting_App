package com.example.matchatapp.feature_chatting.data.interfaces

interface CreateChatRoomResultListener {
    fun onSuccess()
    fun onFailure(error: Throwable)
}