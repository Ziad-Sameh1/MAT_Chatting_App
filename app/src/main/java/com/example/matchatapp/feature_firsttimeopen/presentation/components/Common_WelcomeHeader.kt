package com.example.matchatapp.feature_firsttimeopen.presentation.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CommonWelcomeHeader(modifier: Modifier = Modifier, stringResource: Int) {
    Text(
        text = stringResource(id = stringResource),
        style = MaterialTheme.typography.h3.copy(
            fontWeight = FontWeight.Bold,
            letterSpacing = 2.sp
        ),
        textAlign = TextAlign.Center,
        color = MaterialTheme.colors.onSecondary, modifier = modifier
    )
}