package com.example.matchatapp.feature_chatting.domain.model

sealed class Response<T>(val data: T? = null, val message: String? = null) {
    class Loading<T>(data: T? = null) : Response<T>(data = data)
    class Success<T>(data: T) : Response<T>(data = data)
    class Error<T>(data: T, message: String) : Response<T>(data = data, message = message)
}