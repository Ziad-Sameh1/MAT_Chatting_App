package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import com.example.matchatapp.ui.theme.poppinsFont

/**
 * This is used by both enter ur phone & verify ur phone screeens
 */

@Composable
fun CommonPhoneNumberDesc(
    stringResource: Int,
    appendedStringResource: Int,
    modifier: Modifier = Modifier
) {
    Text(
        text = buildAnnotatedString {
            append(stringResource(id = stringResource))
            append(" ")
            withStyle(style = SpanStyle(color = MaterialTheme.colors.primary)) {
                append(stringResource(id = appendedStringResource))
            }
        },
        style = MaterialTheme.typography.body1.copy(
            fontFamily = poppinsFont,
            fontWeight = FontWeight.Normal
        ),
        modifier = modifier,
        color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
    )
}