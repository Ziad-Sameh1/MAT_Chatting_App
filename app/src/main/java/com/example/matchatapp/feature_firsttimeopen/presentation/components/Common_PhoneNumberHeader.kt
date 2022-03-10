package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.matchatapp.ui.theme.poppinsFont


/**
 * This is used in both enter your phone number screen and verify ur phone number screen
 */
@Composable
fun CommonPhoneNumberHeader(modifier: Modifier = Modifier, stringResource: Int) {
    Text(
        stringResource(id = stringResource),
        style = MaterialTheme.typography.h2.copy(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Bold
        ),
        color = MaterialTheme.colors.onSecondary, modifier = modifier
    )
}