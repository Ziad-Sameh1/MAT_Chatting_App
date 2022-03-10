package com.example.matchatapp.feature_chatting.data.interfaces

interface AddDataToDatabaseResultListener {
    fun onSuccess(isAddedOrUpdated: Boolean)
    fun onFailure(error: Throwable)
}