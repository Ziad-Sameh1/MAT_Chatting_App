package com.example.matchatapp.feature_chatting.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.matchatapp.R

@Composable
fun CommonErrorScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_undraw_access_denied_re_awnf),
                contentDescription = "",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(text = stringResource(id = R.string.error_occurred), style = MaterialTheme.typography.body1, color = MaterialTheme.colors.onSecondary)
        }
    }
}