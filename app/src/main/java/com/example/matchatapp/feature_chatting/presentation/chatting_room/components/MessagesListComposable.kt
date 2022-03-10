package com.example.matchatapp.feature_chatting.presentation.chatting_room.components

import android.content.res.Configuration
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessagesListComposable(
    viewModel: ChattingRoomViewModel,
    configuration: Configuration,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        reverseLayout = true,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(viewModel.messages) { message ->
            if (message.senderId == LoggedInUserData.loggedInUserId)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    MessageItemComposable(
                        message = message,
                        isReceived = false
                    )
                }
            else
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp),
                    horizontalArrangement = Arrangement.Start
                ) {
                    MessageItemComposable(
                        message = message,
                        isReceived = true
                    )
                }
        }
    }
}