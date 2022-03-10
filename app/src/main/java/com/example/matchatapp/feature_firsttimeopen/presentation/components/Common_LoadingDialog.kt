package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel

@Composable
fun CommonLoadingDialog(verifyPhoneNumberViewModel: VerifyPhoneNumberViewModel) {
    Dialog(
        onDismissRequest = { verifyPhoneNumberViewModel.onShowLoadingDialog(false) },
        DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(100.dp)
        ) {
            CircularProgressIndicator()
        }
    }
}