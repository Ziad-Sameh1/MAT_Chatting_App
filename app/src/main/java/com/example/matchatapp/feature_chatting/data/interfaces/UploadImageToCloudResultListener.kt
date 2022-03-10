package com.example.matchatapp.feature_chatting.data.interfaces

import android.net.Uri

interface UploadImageToCloudResultListener {
    fun onSuccess(uri: Uri)
    fun onFailure(error: Throwable)
}