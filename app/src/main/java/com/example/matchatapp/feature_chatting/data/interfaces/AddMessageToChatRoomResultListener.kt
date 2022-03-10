package com.example.matchatapp.feature_chatting.data.interfaces

interface AddMessageToChatRoomResultListener {
    fun onSuccess()
    fun onFailure(error: Throwable)
}