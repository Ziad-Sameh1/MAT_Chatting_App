package com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matchatapp.R
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonPhoneNumberDesc
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonPhoneNumberHeader
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel

@Composable
fun EnterYourPhoneNumberScreen(
    navController: NavController,
    viewModel: EnterYourPhoneNumberViewModel
) {
    val localFocusManager = LocalFocusManager.current
    CommonBackButton(navController = navController)
    Column(
        modifier = Modifier
            .padding(horizontal = 40.dp)
            .padding(top = 50.dp)
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(onTap = { localFocusManager.clearFocus() })
            },
    ) {
        CommonPhoneNumberHeader(
            modifier = Modifier.weight(1f),
            stringResource = R.string.enter_your_phone_screen_header
        )
        CommonPhoneNumberDesc(
            stringResource = R.string.enter_your_phone_screen_desc,
            appendedStringResource = R.string.app_name_used,
            modifier = Modifier.weight(1f)
        )
        EnterYourPhoneNumberComposable(
            viewModel = viewModel, modifier = Modifier
                .fillMaxWidth()
                .weight(2f)
        )
        Row(modifier = Modifier.weight(2f)) {
            Button(
                onClick = {
                    viewModel.checkForm()
                },
                shape = RoundedCornerShape(100.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primary),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.continue_btn),
                    style = MaterialTheme.typography.h6.copy(letterSpacing = 2.sp),
                    modifier = Modifier.padding(horizontal = 30.dp),
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
        EnterYourPhoneNumberPageIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
                .weight(1f)
        )
    }
    EnterYourPhoneNumberAlertDialogs(
        navController = navController,
        viewModel = viewModel,
    )
}