package com.example.matchatapp.feature_chatting.domain.use_case

import android.net.Uri
import com.example.matchatapp.feature_chatting.data.interfaces.UploadImageToCloudResultListener
import com.example.matchatapp.feature_chatting.domain.repository.StorageRepo
import javax.inject.Inject

class GetCloudProfilePicUriUseCase @Inject constructor(
    private val repo: StorageRepo
) {
    operator fun invoke(
        userId: String,
        uri: Uri,
        uploadImageToCloudResultListener: UploadImageToCloudResultListener
    ) {
        return repo.getCloudProfilePicUri(
            userId = userId,
            localImageUri = uri,
            uploadImageToCloudResultListener = uploadImageToCloudResultListener
        )
    }
}