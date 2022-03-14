package com.example.matchatapp.feature_chatting.presentation.chat_list.components

import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.matchatapp.R
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.presentation.chat_list.ChatListViewModel
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel
import com.example.matchatapp.ui.theme.Gray
import com.example.matchatapp.ui.theme.darkMode
import com.example.matchatapp.utils.convertDate
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItem(
    chatRoom: ChatRoom,
    viewModel: ChatListViewModel,
    navController: NavController,
    chattingRoomViewModel: ChattingRoomViewModel
) {
    Surface(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp, horizontal = 15.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) darkMode else Color.White)
                .clickable {
                    /**
                     * Open Chat Room
                     * */
                    // first check
                    viewModel.checkIfChatRoomCreatedBefore(
                        chatRoom.userId[if (viewModel.getAnotherUserIndex(chatRoom = chatRoom) == 1) 0 else 1],
                        User(
                            userId = chatRoom.userId[viewModel.getAnotherUserIndex(chatRoom = chatRoom)],
                            userName = chatRoom.userName[viewModel.getAnotherUserIndex(chatRoom = chatRoom)],
                            userPhone = chatRoom.userId[viewModel.getAnotherUserIndex(chatRoom = chatRoom)],
                            userProfilePic = chatRoom.userProfilePic[viewModel.getAnotherUserIndex(
                                chatRoom = chatRoom
                            )]
                        )
                    )
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box() {
                    Image(
                        if (chatRoom.userProfilePic[viewModel.getAnotherUserIndex(chatRoom = chatRoom)].isNotEmpty()) rememberImagePainter(
                            data = Uri.parse(
                                chatRoom.userProfilePic[viewModel.getAnotherUserIndex(
                                    chatRoom = chatRoom
                                )]
                            ),
                            builder = {
                                transformations(CircleCropTransformation())
                                crossfade(true)
                                fallback(R.drawable.ic_default_user_profile_image_svg)
                                placeholder(R.drawable.ic_image_svgrepo_com)
                                error(R.drawable.ic_default_user_profile_image_svg)
                            }) else
                            painterResource(id = R.drawable.ic_default_user_profile_image_svg),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(100.dp))
                    )
//                    if (chatRoom.isActive == true) {
//                        Image(
//                            painter = painterResource(id = R.drawable.ic_circle),
//                            contentDescription = "",
//                            colorFilter = ColorFilter.tint(color = MaterialTheme.colors.primary),
//                            modifier = Modifier
//                                .size(20.dp)
//                                .align(
//                                    Alignment.BottomEnd
//                                )
//                        )
//                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (chatRoom.userName[viewModel.getAnotherUserIndex(chatRoom = chatRoom)].isNotEmpty()) chatRoom.userName[viewModel.getAnotherUserIndex(
                                chatRoom = chatRoom
                            )] else chatRoom.userId[viewModel.getAnotherUserIndex(chatRoom = chatRoom)],
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colors.onSecondary,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1, modifier = Modifier
                                .fillMaxWidth()
                                .weight(4f)
                        )
                        if (chatRoom.lastMessageType != null) {
                            Text(
                                text = chatRoom.lastActivity.toString().convertDate(),
                                color = MaterialTheme.colors.primary,
                                style = MaterialTheme.typography.caption,
                                letterSpacing = 1.sp,
                                maxLines = 1,
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(6f),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
//                            if (chatRoom.lastMessageType != null) {
//                                Icon(
//                                    painter = painterResource(
//                                        id = if (chatRoom.lastMessageStatus?.containsKey(
//                                                chatRoom.userId[viewModel.getAnotherUserIndex(
//                                                    chatRoom = chatRoom
//                                                )]
//                                            ) == true
//                                        ) R.drawable.ic_circle else R.drawable.ic_outline_circle_24
//                                    ),
//                                    contentDescription = "",
//                                    tint = MaterialTheme.colors.primary,
//                                    modifier = Modifier
//                                        .size(25.dp)
//                                        .weight(1f)
//                                )
//                            }
                            Spacer(modifier = Modifier.width(3.dp))
                            if (chatRoom.lastMessageType != "string" && chatRoom.lastMessageType != null) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_image_icon),
                                    contentDescription = "",
                                    tint = Gray,
                                    modifier = Modifier
                                        .size(30.dp)
                                        .weight(1f)
                                )
                            }
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = chatRoom.lastMessageContent ?: "",
                                style = MaterialTheme.typography.body2,
                                color = MaterialTheme.colors.onSecondary.copy(alpha = 0.5f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.weight(8f)
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                        if (chatRoom.numberOfUnreadMessages[chatRoom.userId[viewModel.getAnotherUserIndex(
                                chatRoom = chatRoom
                            )]] != 0
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .background(
                                            MaterialTheme.colors.primary,
                                            CircleShape
                                        )
                                        .shadow(elevation = 0.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = chatRoom.numberOfUnreadMessages[chatRoom.userId[viewModel.getAnotherUserIndex(
                                            chatRoom = chatRoom
                                        )]].toString(),
                                        style = MaterialTheme.typography.caption,
                                        color = MaterialTheme.colors.onPrimary,
                                        modifier = Modifier
                                            .padding(top = 3.dp)
                                            .fillMaxSize()
                                            .defaultMinSize(
                                                (chatRoom.numberOfUnreadMessages.toString().length * 8).dp,
                                                21.dp
                                            ),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        if (viewModel.isDoneChecking.value) {
            chattingRoomViewModel.readMessagesFromDatabase(viewModel.currentChatRoom.value)
            chattingRoomViewModel.onChattingRoomIdStateChanges(newValue = viewModel.currentChatRoom.value)
            // save the new chat room id
            viewModel.saveChatRoomId(viewModel.currentChatRoom.value)
            navController.navigate(
                Screen.ChattingRoomScreen.route + "?userId=${
                    viewModel.clickedUser.value.userId
                }&userName=${
                    viewModel.clickedUser.value.userName
                }&userPicUri=${
                    URLEncoder.encode(
                        viewModel.clickedUser.value.userProfilePic ?: "",
                        StandardCharsets.UTF_8.toString()
                    )
                }&chatRoomId=${viewModel.currentChatRoom.value}"
            )
            viewModel.onIsDoneCheckingStateChanges(false)
        }
    }
}