package com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber

import android.app.Activity
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_firsttimeopen.domain.use_case.SendVerificationCodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EnterYourPhoneNumberViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase
) : ViewModel() {

    private var _countryCode = mutableStateOf<String>("+20")
    val countryCode: State<String> = _countryCode

    private var _phoneNumber = mutableStateOf<String>("")
    val phoneNumber: State<String> = _phoneNumber

    private var _fullNumber = mutableStateOf<String>(_countryCode.value + _phoneNumber.value)
    val fullNumber: State<String> = _fullNumber

    private var _countryCodeErrorState = mutableStateOf<Boolean>(false)
    val countryCodeErrorState: State<Boolean> = _countryCodeErrorState

    private var _phoneNumberErrorState = mutableStateOf<Boolean>(false)
    val phoneNumberErrorState: State<Boolean> = _phoneNumberErrorState

    private var _showVerificationDialogState = mutableStateOf<Boolean>(false)
    val showVerificationDialogState: State<Boolean> = _showVerificationDialogState

    fun onCountryCodeChanges(newValue: String) {
        _countryCode.value = newValue
        _fullNumber.value = _countryCode.value + _phoneNumber.value
    }

    fun onPhoneNumberChanges(newValue: String) {
        _phoneNumber.value = newValue
        _fullNumber.value = _countryCode.value + _phoneNumber.value
    }

    fun onCountryCodeErrorStateChanges(newValue: Boolean) {
        _countryCodeErrorState.value = newValue
    }

    fun onPhoneNumberErrorStateChanges(newValue: Boolean) {
        _phoneNumberErrorState.value = newValue
    }

    fun onShowVerificationDialogStateChanges(newValue: Boolean) {
        _showVerificationDialogState.value = newValue
    }

    fun checkForm() {
        if (countryCode.value.isBlank()) {
            onCountryCodeErrorStateChanges(true)
        } else if (phoneNumber.value.isBlank() || phoneNumber.value.length <= 7) {
            onPhoneNumberErrorStateChanges(true)

        } else {
            onShowVerificationDialogStateChanges(true)
        }
    }

    fun sendVerificationCode(activity: Activity) {
        sendVerificationCodeUseCase(_fullNumber.value, activity = activity)
        LoggedInUserData.loggedInUserPhoneNumber = _fullNumber.value
    }
}