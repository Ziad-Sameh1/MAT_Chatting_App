package com.example.matchatapp.feature_chatting.presentation.profile_screen

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_chatting.data.interfaces.AddDataToDatabaseResultListener
import com.example.matchatapp.feature_chatting.data.interfaces.UploadImageToCloudResultListener
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.domain.use_case.AddOrUpdateUserDataUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.GetCloudProfilePicUriUseCase
import com.example.matchatapp.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val addOrUpdateUserDataUseCase: AddOrUpdateUserDataUseCase,
    private val getCloudProfilePicUriUseCase: GetCloudProfilePicUriUseCase
) : ViewModel() {

    private val _userName = mutableStateOf<String>(LoggedInUserData.loggedInUserName)
    val userName: State<String> = _userName

    private val _userDescription = mutableStateOf<String>(LoggedInUserData.loggedInUserBio)
    val userDescription: State<String> = _userDescription

    private val _cloudProfileImageUri =
        mutableStateOf<String?>(LoggedInUserData.loggedInUserprofilePicUri)
    val cloudProfileImageUri: State<String?> = _cloudProfileImageUri

    private val _localProfileImageUri = mutableStateOf<String?>("")
    val localProfileImageUri: State<String?> = _localProfileImageUri

    private val _userPhoneNumber = mutableStateOf<String>(LoggedInUserData.loggedInUserPhoneNumber)
    val userPhoneNumber: State<String> = _userPhoneNumber

    private val _userId = mutableStateOf<String>(LoggedInUserData.loggedInUserPhoneNumber)
    val userId: State<String> = _userId

    private val _isEditable = mutableStateOf<Boolean>(false)
    val isEditable: State<Boolean> = _isEditable

    private val _isLoadingState = mutableStateOf<Boolean>(false)
    val isLoadingState: State<Boolean> = _isLoadingState

    private val _isErrorState = mutableStateOf<Boolean>(false)
    val isErrorState: State<Boolean> = _isErrorState

    private val _isSavedState = mutableStateOf<Boolean>(false)
    val isSavedState: State<Boolean> = _isSavedState

    private val _isProfilePicLoadingState = mutableStateOf<Boolean>(false)
    val isProfilePicLoadingState: State<Boolean> = _isProfilePicLoadingState


    fun onUserNameChanges(newValue: String) {
        _userName.value = newValue
    }

    fun onUserDescriptionChanges(newValue: String) {
        _userDescription.value = newValue
    }

    fun onEditableStateChanges(newValue: Boolean) {
        _isEditable.value = newValue
    }

    fun onLocalProfileImageUriChanges(newValue: Uri?) {
        _localProfileImageUri.value = newValue.toString()
    }

    fun onUserPhoneNumberChanges(newValue: String) {
        _userPhoneNumber.value = newValue
        _userId.value = newValue
    }

    fun onIsLoadingStateChanges(newValue: Boolean) {
        _isLoadingState.value = newValue
    }

    fun onIsErrorStateChanges(newValue: Boolean) {
        _isErrorState.value = newValue
    }

    fun onIsSavedStateChanges(newValue: Boolean) {
        _isSavedState.value = newValue
    }

    fun onIsProfilePicLoadingStateChanges(newValue: Boolean) {
        _isProfilePicLoadingState.value = newValue
    }

    fun onAddOrUpdateUserData() {
        addOrUpdateUserDataUseCase(
            User(
                userId = userId.value,
                userName = userName.value,
                userBio = userDescription.value,
                userPhone = userPhoneNumber.value,
                userProfilePic = cloudProfileImageUri.value
            ), object : AddDataToDatabaseResultListener {
                override fun onSuccess(isAddedOrUpdated: Boolean) {
                    Log.i(TAG, "onAddOrUpdateUserData: Added Successfully")
                    _isLoadingState.value = false
                    _isSavedState.value = true
                }

                override fun onFailure(error: Throwable) {
                    Log.i(TAG, "onFailure: error -> ${error.message}")
                    _isLoadingState.value = false
                    _isErrorState.value = true
                }

            }
        )
    }

    fun getCloudProfileImageUri(uri: Uri?) {
        getCloudProfilePicUriUseCase(
            userId = userId.value,
            uri = uri ?: Uri.parse(""),
            uploadImageToCloudResultListener = object : UploadImageToCloudResultListener {
                override fun onSuccess(uri: Uri) {
                    _isProfilePicLoadingState.value = false
                    _cloudProfileImageUri.value = uri.toString()
                    Log.i(TAG, "onSuccess: cloudUri -> ${_cloudProfileImageUri.value}")
                }

                override fun onFailure(error: Throwable) {
                    _isProfilePicLoadingState.value = false
                    Log.i(TAG, "ProfilePicUploadingFailed: error -> ${error.message}")
                }

            })
    }

    fun verifyForm(): Boolean {
        return if (userName.value.isNotEmpty() && userPhoneNumber.value.isNotEmpty()) {
            true
        } else {
            _isErrorState.value = true
            false
        }
    }
}