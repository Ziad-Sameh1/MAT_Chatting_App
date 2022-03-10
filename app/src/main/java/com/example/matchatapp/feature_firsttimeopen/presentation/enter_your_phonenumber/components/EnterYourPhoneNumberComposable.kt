package com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchatapp.R
import com.example.matchatapp.feature_firsttimeopen.presentation.enter_your_phonenumber.EnterYourPhoneNumberViewModel
import com.example.matchatapp.ui.theme.poppinsFont

@Composable
fun EnterYourPhoneNumberComposable(
    viewModel: EnterYourPhoneNumberViewModel,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly, modifier = modifier
    ) {
        Column() {
            Text(
                stringResource(id = R.string.country_code),
                fontFamily = poppinsFont,
                color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.Bold
            )
            TextField(
                value = viewModel.countryCode.value,
                onValueChange = { newValue ->
                    if (newValue.length <= 4) viewModel.onCountryCodeChanges(newValue = newValue)
                    if (newValue.isBlank()) viewModel.onCountryCodeChanges("+")
                },
                modifier = Modifier.width(100.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSecondary
                ),
                textStyle = MaterialTheme.typography.body2.copy(
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1, isError = viewModel.countryCodeErrorState.value
            )
        }
        Spacer(modifier = Modifier.width(4.dp))
        Column {
            Text(
                stringResource(id = R.string.phone_number),
                fontFamily = poppinsFont,
                color = MaterialTheme.colors.onSecondary, fontWeight = FontWeight.Bold
            )
            TextField(
                value = viewModel.phoneNumber.value,
                onValueChange = { newValue ->
                    if (newValue.length <= 12) viewModel.onPhoneNumberChanges(newValue = newValue)
                },
                modifier = Modifier.width(180.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = MaterialTheme.colors.onSecondary
                ), isError = viewModel.phoneNumberErrorState.value,
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.phone_number),
                        style = MaterialTheme.typography.body2.copy(
                            fontFamily = poppinsFont,
                            letterSpacing = 1.sp
                        )
                    )
                },
                textStyle = MaterialTheme.typography.body2.copy(
                    fontFamily = poppinsFont,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1
            )
        }
    }
}