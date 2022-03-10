package com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.matchatapp.feature_firsttimeopen.presentation.verify_your_phone_number.VerifyPhoneNumberViewModel
import com.example.matchatapp.ui.theme.poppinsFont

@Composable
fun VerifyPhoneNumberComposable(
    modifier: Modifier = Modifier,
    focusManager: FocusManager,
    viewModel: VerifyPhoneNumberViewModel
) {
    Row(
        modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            value = viewModel.num1.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum1Changes(newValue = newValue)
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (newValue.isEmpty()) {
                    viewModel.onNum1Changes("")
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.width(3.dp))
        TextField(
            value = viewModel.num2.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum2Changes(newValue = newValue)
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (newValue.isEmpty()) {
                    viewModel.onNum2Changes(newValue = "")
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.width(3.dp))
        TextField(
            value = viewModel.num3.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum3Changes(newValue = newValue)
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (newValue.isEmpty()) {
                    viewModel.onNum3Changes(newValue = "")
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.width(3.dp))
        TextField(
            value = viewModel.num4.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum4Changes(newValue = newValue)
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (newValue.isEmpty()) {
                    viewModel.onNum4Changes(newValue = "")
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.width(3.dp))
        TextField(
            value = viewModel.num5.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum5Changes(newValue = newValue)
                    focusManager.moveFocus(FocusDirection.Next)
                } else if (newValue.isEmpty()) {
                    viewModel.onNum5Changes(newValue = "")
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(Modifier.width(3.dp))
        TextField(
            value = viewModel.num6.value,
            onValueChange = { newValue ->
                if (newValue.length == 1) {
                    viewModel.onNum6Changes(newValue = newValue)
                    focusManager.clearFocus()
                } else if (newValue.isEmpty()) {
                    viewModel.onNum6Changes(newValue = "")
                    focusManager.moveFocus(FocusDirection.Previous)
                }
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                textColor = MaterialTheme.colors.primary
            ),
            textStyle = MaterialTheme.typography.h3.copy(
                fontFamily = poppinsFont,
                fontWeight = FontWeight.ExtraBold,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
    }
}