package com.example.matchatapp.feature_chatting.presentation.add_group_screen.components

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.R
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.presentation.add_group_screen.AddGroupViewModel
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel
import com.example.matchatapp.ui.theme.darkMode
import com.example.matchatapp.utils.Constants.TAG
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun UserItemComposable(
    user: User,
    navController: NavController,
    viewModel: AddGroupViewModel,
    chattingRoomViewModel: ChattingRoomViewModel
) {
    Surface(
        elevation = 5.dp,
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(if (isSystemInDarkTheme()) darkMode else Color.White)
                .clickable {

                    /**
                     * First Check if the chat room is created before if so return the id else create a new one
                     * */
                    viewModel.onIsLoadingStateChanges(true)
                    viewModel.checkIfChatRoomCreatedBefore(
                        LoggedInUserData.loggedInUserId,
                        user
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
                        painter =
                        if (user.userProfilePic?.isNotEmpty() == true) rememberImagePainter(
                            data = Uri.parse(user.userProfilePic),
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
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.userName ?: "",
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = MaterialTheme.colors.onSecondary,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1, modifier = Modifier
                                .fillMaxWidth()
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = user.userBio ?: "",
                                style = MaterialTheme.typography.body2.copy(letterSpacing = 1.sp),
                                color = MaterialTheme.colors.onSecondary.copy(alpha = 0.5f),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                        }
                    }
                }
            }
        }
    }
    if (viewModel.doNavigate.value) {
        chattingRoomViewModel.readMessagesFromDatabase(chatRoomId = viewModel.currentChatRoom.value)
        chattingRoomViewModel.onChattingRoomIdStateChanges(newValue = viewModel.currentChatRoom.value)
        viewModel.onIsLoadingStateChanges(false)
        // save the new chat room id
        viewModel.saveChatRoomId(chatRoomId = viewModel.currentChatRoom.value)
        navController.navigate(
            Screen.ChattingRoomScreen.route + "?userId=${viewModel.clickedUser.value.userId}&userName=${viewModel.clickedUser.value.userName}&userPicUri=${
                URLEncoder.encode(
                    viewModel.clickedUser.value.userProfilePic ?: "",
                    StandardCharsets.UTF_8.toString()
                )
            }&chatRoomId=${viewModel.currentChatRoom.value}"
        ) {
            popUpTo(Screen.ChatListScreen.route)
        }

        viewModel.onDoNavigateStateChanges(newValue = false)
    }
    if (viewModel.isDoneChecking.value) {
        if (viewModel.currentChatRoom.value.isEmpty()) {
            // then create chat room
            viewModel.createChatRoom(
                chatRoom = ChatRoom(
                    chatRoomId = LoggedInUserData.loggedInUserId + "_" + viewModel.clickedUser.value.userId,
                    userId = listOf(
                        LoggedInUserData.loggedInUserId,
                        viewModel.clickedUser.value.userId
                    ),
                    userName = listOf(
                        LoggedInUserData.loggedInUserName,
                        viewModel.clickedUser.value.userName ?: ""
                    ),
                    userProfilePic = listOf(
                        LoggedInUserData.loggedInUserprofilePicUri,
                        viewModel.clickedUser.value.userProfilePic ?: ""
                    ),
                    numberOfUnreadMessages = hashMapOf(
                        LoggedInUserData.loggedInUserId to 0,
                        viewModel.clickedUser.value.userId to 0
                    ),
                    numberOfParticipants = 2
                )
            )
            viewModel.onIsDoneCheckingStateChanges(false)
            chattingRoomViewModel.onReadingMessageStateDone(false)
        } else {
            // then the chat room is found and its id is stored in viewModel.currentChatRoom
            viewModel.onDoNavigateStateChanges(true)
            viewModel.onIsDoneCheckingStateChanges(false)
            chattingRoomViewModel.onReadingMessageStateDone(false)
        }
    }
    if (viewModel.isLoadingState.value) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}