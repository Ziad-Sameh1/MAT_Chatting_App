package com.example.matchatapp.feature_chatting.data.interfaces

interface CheckIfChatRoomCreatedBeforeResultListener {
    fun onSuccess(chatRoomId: String)
    fun onNotFound()
}