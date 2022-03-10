package com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel

@Composable
fun VerifyPhoneNumberAlertDialog(viewModel: VerifyPhoneNumberViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onEmptyFields(false)
        },
        title = {
            Text(text = "Wrong Verification Code!", color = MaterialTheme.colors.error)
        },
        text = {
            Column() {
                Text("Please enter a correct verification code")
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onEmptyFields(false) }
                ) {
                    Text("Try Again")
                }
            }
        }
    )
}