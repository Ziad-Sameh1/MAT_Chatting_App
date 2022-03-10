package com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.components

import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.R
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonLoadingDialog
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonPhoneNumberHeader
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel
import com.example.matchatapp.ui.theme.poppinsFont
import com.example.matchatapp.utils.Constants
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*
import kotlin.concurrent.schedule

@OptIn(DelicateCoroutinesApi::class)
@Composable
fun VerifyPhoneNumberScreen(
    navController: NavController,
    phoneNumber: String?,
    verifyPhoneNumberViewModel: VerifyPhoneNumberViewModel,
    enterYourPhoneNumberViewModel: EnterYourPhoneNumberViewModel,
    sharedPreferences: SharedPreferences, sharedEditor: SharedPreferences.Editor
) {
    val focusManager = LocalFocusManager.current
    CommonBackButton(navController = navController)
    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 50.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { focusManager.clearFocus() })
            },
    ) {
        CommonPhoneNumberHeader(
            stringResource = R.string.verify_phone_number_screen_header,
            modifier = Modifier.weight(2f)
        )
        Text(
            text = buildAnnotatedString {
                append(stringResource(id = R.string.verify_phone_number_screen_desc))
                append(" ")
                withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                    (phoneNumber ?: sharedPreferences.getString(
                        Constants.USER_FULL_PHONE_NUMBER,
                        ""
                    ))?.let {
                        append(
                            it
                        )
                    }
                }
            },
            style = MaterialTheme.typography.body1.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.Normal
            ),
            modifier = Modifier
                .weight(1f),
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
        )
        VerifyPhoneNumberComposable(
            modifier = Modifier.weight(2f),
            viewModel = verifyPhoneNumberViewModel,
            focusManager = focusManager
        )
        Column(modifier = Modifier.weight(2f), verticalArrangement = Arrangement.SpaceEvenly) {
            VerifyPhoneNumberTimerComposable(
                verifyPhoneNumberViewModel = verifyPhoneNumberViewModel,
                enterYourPhoneNumberViewModel = enterYourPhoneNumberViewModel
            )
            Row {
                Button(
                    onClick = {
                        if (verifyPhoneNumberViewModel.checkForm()) {
                            verifyPhoneNumberViewModel.onEmptyFields(false)
                            verifyPhoneNumberViewModel.verifySmsCode()
                            verifyPhoneNumberViewModel.refreshSigningInStatus()
                            if (verifyPhoneNumberViewModel.signingInStatus.value == Constants.LOADING) {
                                verifyPhoneNumberViewModel.onShowLoadingDialog(true)
                            }
                            Timer().schedule(2500) {
                                if (verifyPhoneNumberViewModel.signingInStatus.value == Constants.LOADING) {
                                    Log.i(Constants.TAG, "VerifyPhoneNumberScreen: Loading")
                                    verifyPhoneNumberViewModel.onShowLoadingDialog(true)
                                    verifyPhoneNumberViewModel.refreshSigningInStatus()
                                    Log.i(Constants.TAG, "VerifyPhoneNumberScreen: inside timer")
                                }
                                Log.i(Constants.TAG, "VerifyPhoneNumberScreen: xxxxxxxxxxxxxxx")
                                if (verifyPhoneNumberViewModel.signingInStatus.value == Constants.VERIFIED) {
                                    Log.i(Constants.TAG, "VerifyPhoneNumberScreen: Verified")
                                    verifyPhoneNumberViewModel.onShowLoadingDialog(false)
                                    sharedEditor.putString(
                                        Constants.VERIFICATION_STATUS,
                                        Constants.VERIFIED
                                    )
                                    sharedEditor.commit()
                                    sharedEditor.apply()
                                } else if (verifyPhoneNumberViewModel.signingInStatus.value == Constants.FAILED) {
                                    Log.i(Constants.TAG, "VerifyPhoneNumberScreen: NotVerified")
                                    verifyPhoneNumberViewModel.onShowLoadingDialog(false)
                                    verifyPhoneNumberViewModel.onShowAlertDialogsChanges(newValue = true)
                                }
                            }
                        } else {
                            verifyPhoneNumberViewModel.onEmptyFields(true)
                        }
                    },
                    shape = RoundedCornerShape(100.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(id = R.string.verify),
                        style = MaterialTheme.typography.h6.copy(letterSpacing = 2.sp),
                        modifier = Modifier.padding(horizontal = 30.dp),
                        color = MaterialTheme.colors.onPrimary
                    )
                }
            }
        }
        if (verifyPhoneNumberViewModel.showAlertDialogs.value)
            VerificationFailedAlertDialogs(viewModel = verifyPhoneNumberViewModel)
        if (verifyPhoneNumberViewModel.showLoadingDialog.value)
            CommonLoadingDialog(verifyPhoneNumberViewModel = verifyPhoneNumberViewModel)
        if (verifyPhoneNumberViewModel.signingInStatus.value == Constants.VERIFIED) {
            navController.navigate(Screen.EnterPersonalInfoScreen.route) {
                popUpTo(Screen.EnterPersonalInfoScreen.route)
            }
        }
        if (verifyPhoneNumberViewModel.emptyFields.value) {
            VerifyPhoneNumberAlertDialog(viewModel = verifyPhoneNumberViewModel)
        }
        VerifyPhoneNumberPageIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .weight(1f)
        )
    }
}


