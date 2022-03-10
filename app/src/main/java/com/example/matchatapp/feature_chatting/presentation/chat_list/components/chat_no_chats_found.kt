package com.example.matchatapp.feature_chatting.presentation.chat_list.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.matchatapp.R

@Composable
fun NoChatsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 30.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_undraw_add_chats),
                contentDescription = "",
                modifier = Modifier.size(250.dp)
            )
            Spacer(modifier = Modifier.height(80.dp))
            Text(
                text = stringResource(id = R.string.click_on_add_users),
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onSecondary,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun Preview1() {
    NoChatsFound()
}