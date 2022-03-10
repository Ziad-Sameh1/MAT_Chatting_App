package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CommonWelcomeDesc(modifier: Modifier = Modifier, stringResource: Int) {
    Text(
        text = stringResource(id = stringResource),
        style = MaterialTheme.typography.body2.copy(lineHeight = 21.sp),
        textAlign = TextAlign.Center,
        modifier = modifier
            .padding(horizontal = 20.dp),
        color = MaterialTheme.colors.onSecondary.copy(alpha = 0.7f)
    )
}