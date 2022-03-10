package com.example.matchatapp.feature_chatting.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import android.webkit.MimeTypeMap
import com.example.matchatapp.feature_chatting.data.interfaces.UploadImageToCloudResultListener
import com.example.matchatapp.feature_chatting.domain.repository.StorageRepo
import com.example.matchatapp.utils.Constants.TAG
import com.google.firebase.storage.StorageReference
import javax.inject.Inject

class StorageRepoImpl @Inject constructor(
    private val storageReference: StorageReference,
    private val context: Context
) : StorageRepo {
    override fun getCloudProfilePicUri(
        userId: String,
        localImageUri: Uri?,
        uploadImageToCloudResultListener: UploadImageToCloudResultListener
    ) {

        /**
         * THE NEXT THREE LINES ARE TO GET THE IMAGE EXTENSION (EX: JPEG) FROM THE URI
         * */
        val contentResolver = context.contentResolver
        val mimeTypeMap = MimeTypeMap.getSingleton()
        val imageType =
            mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(localImageUri!!))

        val fileRef = storageReference.child("profile_pics/$userId.$imageType")

        val uploadTask = fileRef.putFile(localImageUri) // Upload file

        uploadTask.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    uploadImageToCloudResultListener.onFailure(it)
                }
            }
            fileRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                uploadImageToCloudResultListener.onSuccess(uri = task.result)
            }
        }
    }
}