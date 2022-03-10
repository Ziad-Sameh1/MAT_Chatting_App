package com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number

import android.os.CountDownTimer
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.matchatapp.feature_firsttimeopen.domain.use_case.GetSigningInStatusUseCase
import com.example.matchatapp.feature_firsttimeopen.domain.use_case.SignInTheUserUseCase
import com.example.matchatapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerifyPhoneNumberViewModel @Inject constructor(
    private val signInTheUserUseCase: SignInTheUserUseCase,
    private val signingInStatusUseCase: GetSigningInStatusUseCase
) : ViewModel() {
    private lateinit var timer: CountDownTimer

    init {
        startTimer()
    }

    private val _num1 = mutableStateOf<String>("")
    val num1: State<String> = _num1

    private val _num2 = mutableStateOf<String>("")
    val num2: State<String> = _num2

    private val _num3 = mutableStateOf<String>("")
    val num3: State<String> = _num3

    private val _num4 = mutableStateOf<String>("")
    val num4: State<String> = _num4

    private val _num5 = mutableStateOf<String>("")
    val num5: State<String> = _num5

    private val _num6 = mutableStateOf<String>("")
    val num6: State<String> = _num6

    private var _smsCode =
        _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value

    private val _timerValue = mutableStateOf<String>("")
    val timerValue: State<String> = _timerValue

    private val _timerState = mutableStateOf<Boolean>(true)
    val timerState: State<Boolean> = _timerState

    private val _showAlertDialogs = mutableStateOf<Boolean>(false)
    val showAlertDialogs: State<Boolean> = _showAlertDialogs

    private val _signingInStatus = mutableStateOf<String>("")
    val signingInStatus: State<String> = _signingInStatus

    private val _showLoadingDialog = mutableStateOf<Boolean>(false)
    val showLoadingDialog: State<Boolean> = _showLoadingDialog

    private val _emptyFields = mutableStateOf<Boolean>(false)
    val emptyFields: State<Boolean> = _emptyFields

    fun onEmptyFields(newValue: Boolean) {
        _emptyFields.value = newValue
    }

    fun onShowAlertDialogsChanges(newValue: Boolean) {
        _showAlertDialogs.value = newValue
    }

    fun onShowLoadingDialog(newValue: Boolean) {
        _showLoadingDialog.value = newValue
    }

    fun onNum1Changes(newValue: String) {
        _num1.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    fun onNum2Changes(newValue: String) {
        _num2.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    fun onNum3Changes(newValue: String) {
        _num3.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    fun onNum4Changes(newValue: String) {
        _num4.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    fun onNum5Changes(newValue: String) {
        _num5.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    fun onNum6Changes(newValue: String) {
        _num6.value = newValue
        _smsCode =
            _num1.value + _num2.value + _num3.value + _num4.value + _num5.value + _num6.value
    }

    private fun startTimer() {
        timer = object : CountDownTimer(80000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timerValue.value =
                    if (millisUntilFinished > 70000) "01:${(millisUntilFinished - 60000) / 1000}"
                    else if (millisUntilFinished > 60000) "01:0${(millisUntilFinished - 60000) / 1000}"
                    else if (millisUntilFinished > 10000) "00:${millisUntilFinished / 1000}" else "00:0${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                _timerState.value = false
            }
        }.start()
    }

    fun restartTimer() {
        if (!_timerState.value) {
            timer.cancel()
            _timerState.value = true
            timer.start()
        }
    }

    fun verifySmsCode() {
        signInTheUserUseCase(smsCode = _smsCode)
    }

    fun refreshSigningInStatus() {
        _signingInStatus.value = signingInStatusUseCase()
        Log.i(Constants.TAG, "refreshSigningInStatus: ${_signingInStatus.value}")
    }

    fun checkForm(): Boolean {
        return num1.value != "" && num2.value != "" && num3.value != "" && num4.value != "" && num5.value != "" && num6.value != ""
    }
}
