package com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.example.matchatapp.R
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel

@Composable
fun VerifyPhoneNumberTimerComposable(
    verifyPhoneNumberViewModel: VerifyPhoneNumberViewModel,
    modifier: Modifier = Modifier, enterYourPhoneNumberViewModel: EnterYourPhoneNumberViewModel
) {
    val localContext = LocalContext.current
    if (verifyPhoneNumberViewModel.timerState.value) {
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.verify_screen_you_can_send_verification_again))
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    append(verifyPhoneNumberViewModel.timerValue.value)
                }
            },
            modifier = modifier,
            style = MaterialTheme.typography.body2
        )
    } else {
        Row(modifier = modifier) {
            Text(
                text = stringResource(id = R.string.verify_screen_resend_code) + " ",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = stringResource(id = R.string.resend_code_string),
                style = MaterialTheme.typography.body2,
                color = MaterialTheme.colors.primary,
                modifier = Modifier.clickable {
                    enterYourPhoneNumberViewModel.sendVerificationCode(localContext.findActivity()!!)
                    verifyPhoneNumberViewModel.restartTimer()
                }
            )
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}