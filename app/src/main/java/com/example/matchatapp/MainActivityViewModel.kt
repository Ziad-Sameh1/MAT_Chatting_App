package com.example.matchatapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matchatapp.feature_chatting.data.interfaces.GetUserPersonalInfoResultListener
import com.example.matchatapp.feature_chatting.domain.use_case.GetDeviceTokenUseCase
import com.example.matchatapp.feature_chatting.domain.use_case.GetUserPersonalDataUseCase
import com.example.matchatapp.feature_firsttimeopen.domain.use_case.GetUserDataUseCase
import com.example.matchatapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getUserDataUseCase: GetUserDataUseCase,
    private val getUserPersonalDataUseCase: GetUserPersonalDataUseCase,
    private val getDeviceTokenUseCase: GetDeviceTokenUseCase
) : ViewModel() {

    private val userPhoneNumber = getUserDataUseCase()?.phoneNumber

    private val _loadingState = mutableStateOf<Boolean>(true)
    val loadingState: State<Boolean> = _loadingState

    init {
        if (userPhoneNumber == null) {
            _loadingState.value = false
        } else {
            getUserPersonalDataUseCase(
                userPhoneNumber = userPhoneNumber,
                getUserPersonalInfoResultListener = object : GetUserPersonalInfoResultListener {
                    override fun onSuccess() {
                        _loadingState.value = false
                        Log.i(Constants.TAG, "onSuccess: ${LoggedInUserData.loggedInUserId}")
                    }

                })
            getDeviceTokenUseCase()
        }
    }
}