package com.example.matchatapp.feature_firsttimeopen.presentation.enter_personal_info

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matchatapp.feature_chatting.presentation.profile_screen.ProfileViewModel

@Composable
fun EnterPersonalInfoAlertDialog(viewModel: ProfileViewModel) {
    AlertDialog(
        onDismissRequest = {
            viewModel.onIsErrorStateChanges(false)
        },
        title = {
            Text(text = "Error!", color = MaterialTheme.colors.error)
        },
        text = {
            Column() {
                Text("An error occurred, Please try again.")
            }
        },
        buttons = {
            Row(
                modifier = Modifier.padding(all = 8.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { viewModel.onIsErrorStateChanges(false) }
                ) {
                    Text("Understood!")
                }
            }
        }
    )
}