package com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel
import com.example.matchatapp.utils.removeExtraZeroPhone

@Composable
fun EnterYourPhoneNumberAlertDialogs(
    navController: NavController,
    viewModel: EnterYourPhoneNumberViewModel,
) {
    // reference for activity
    val context = LocalContext.current
    if (viewModel.countryCodeErrorState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onCountryCodeErrorStateChanges(false)
            },
            title = {
                Text(text = "Error!", color = MaterialTheme.colors.error)
            },
            text = {
                Column() {
                    Text("Please enter your country code!")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onCountryCodeErrorStateChanges(false) }
                    ) {
                        Text("OK")
                    }
                }
            }
        )
    } else if (viewModel.phoneNumberErrorState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onPhoneNumberErrorStateChanges(false)
            },
            title = {
                Text(text = "Error!", color = MaterialTheme.colors.error)
            },
            text = {
                Column() {
                    Text("Please enter your phone number!")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = { viewModel.onPhoneNumberErrorStateChanges(false) }
                    ) {
                        Text("OK")
                    }
                }
            }
        )

    }
    if (viewModel.showVerificationDialogState.value) {
        AlertDialog(
            onDismissRequest = {
                viewModel.onShowVerificationDialogStateChanges(false)
            },
            title = {
                Text(text = "Confirm", color = MaterialTheme.colors.error)
            },
            text = {
                Column() {
                    Text("Do you want to login with this number: ${viewModel.fullNumber.value}")
                }
            },
            buttons = {
                Row(
                    modifier = Modifier.padding(all = 8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                            viewModel.onShowVerificationDialogStateChanges(false)
                            context.findActivity()?.let { viewModel.sendVerificationCode(it) }
                            /**
                             * Next, we will change the value saved in shared preferences which is the verification
                             * status to in_progress, so if the user closed the app before enters the verification
                             * code, the app will launches directly to the verification screen.
                             * */
                            navController.navigate(Screen.VerifyPhoneNumberScreen.route + "/${if (viewModel.phoneNumber.value.length > 13) viewModel.phoneNumber.value.removeExtraZeroPhone() else viewModel.phoneNumber.value}") {
                                popUpTo(Screen.EnterYourPhoneNumberScreen.route)
                            }
                            viewModel.onCountryCodeErrorStateChanges(false)
                            viewModel.onPhoneNumberErrorStateChanges(false)
                        }
                    ) {
                        Text("Yes")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.onShowVerificationDialogStateChanges(false) }
                    ) {
                        Text("No")
                    }
                }
            }
        )
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}