package com.example.matchatapp.feature_chatting.domain.repository

import android.net.Uri
import com.example.matchatapp.feature_chatting.data.interfaces.UploadImageToCloudResultListener

interface StorageRepo {
    fun getCloudProfilePicUri(userId: String, localImageUri : Uri?, uploadImageToCloudResultListener: UploadImageToCloudResultListener)
}