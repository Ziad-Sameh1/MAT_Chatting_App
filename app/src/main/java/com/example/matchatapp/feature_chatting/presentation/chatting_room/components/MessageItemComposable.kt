package com.example.matchatapp.feature_chatting.presentation.chatting_room.components

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.utils.convertDate
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MessageItemComposable(
    message: Message, isReceived: Boolean, modifier: Modifier = Modifier
) {
    Card(
        elevation = 2.dp,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        backgroundColor = if (!isReceived) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 5.dp)
        ) {
            Column() {
                Column(modifier = Modifier.padding(end = 40.dp)) {
                    Text(
                        text = message.content ?: "",
                        textAlign = TextAlign.Start,
                        style = if (message.content == "\uD83D\uDE02" || message.content == "❤" || message.content == "\uD83E\uDD23" || message.content == "\uD83D\uDC4D" || message.content == "\uD83E\uDD70" || message.content == "\uD83E\uDD7A" || message.content == "\uD83D\uDE2D" || message.content == "\uD83D\uDE21" || message.content == "\uD83D\uDE1E" || message.content == "\uD83D\uDE22" || message.content == "\uD83E\uDD21" || message.content == "\uD83E\uDD29" || message.content == "\uD83D\uDC8B" || message.content == "\uD83D\uDE42" || message.content == "\uD83D\uDE14" || message.content == "\uD83D\uDE18" || message.content == "\uD83D\uDE0A" || message.content == "\uD83D\uDE0B" || message.content == "\uD83E\uDD74" || message.content == "\uD83D\uDE32" || message.content == "\uD83D\uDE0D" || message.content == "\uD83E\uDD2F" || message.content == "\uD83D\uDE0E") MaterialTheme.typography.h1 else MaterialTheme.typography.body1
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(
                        text = if (message.timeSent != null) SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(
                            message.timeSent
                        )
                            .convertDate() else "",
                        textAlign = TextAlign.End,
                        style = MaterialTheme.typography.caption.copy(fontSize = 10.sp)
                    )
                }
            }
        }
    }

}