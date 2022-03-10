package com.example.matchatapp.feature_chatting.presentation.chatting_room

import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.R
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.presentation.chatting_room.components.MessagesListComposable
import com.example.matchatapp.feature_firsttimeopen.presentation.components.CommonBackButton
import com.example.matchatapp.ui.theme.CardGray

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChattingRoomScreen(
    configuration: Configuration,
    viewModel: ChattingRoomViewModel,
    navController: NavController,
    userId: String = "",
    userName: String = "",
    userPicUri: String = "",
    focusManager: FocusManager
) {
    Column(modifier = Modifier.fillMaxSize()) {
        /**
         * Upper Row
         * */
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = if (isSystemInDarkTheme()) CardGray else MaterialTheme.colors.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CommonBackButton(
                navController = navController,
                tint = MaterialTheme.colors.onPrimary
            )
            Image(
                painter =
                if (userPicUri.isNotEmpty()) rememberImagePainter(
                    data = Uri.parse(userPicUri),
                    builder = {
                        transformations(CircleCropTransformation())
                        crossfade(true)
                        fallback(R.drawable.ic_default_user_profile_image_svg)
                        placeholder(R.drawable.ic_image_svgrepo_com)
                        error(R.drawable.ic_default_user_profile_image_svg)
                    }) else
                    painterResource(id = R.drawable.ic_default_user_profile_image_svg),
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .size(40.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = userName.ifEmpty { userId },
                color = MaterialTheme.colors.onPrimary,
                style = MaterialTheme.typography.h6.copy(letterSpacing = 1.sp),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            /**
             * Messages List
             * */
            MessagesListComposable(
                viewModel = viewModel,
                configuration = configuration,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 75.dp)
                    .padding(top = 5.dp)
            )
            /**
             * Bottom Row
             * **/

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        color = MaterialTheme.colors.surface
                    )
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = viewModel.enteredMessageState.value,
                    onValueChange = { newValue -> viewModel.onEnteredMessageStateChanges(newValue = newValue) },
                    modifier = Modifier
                        .fillMaxWidth(0.8f),
                    shape = RoundedCornerShape(35.dp),
                    placeholder = {
                        Text(
                            stringResource(id = R.string.message),
                            style = MaterialTheme.typography.body1.copy(letterSpacing = 1.sp),
                            color = MaterialTheme.colors.primary.copy(alpha = 0.7f)
                        )
                    },
                    textStyle = MaterialTheme.typography.body1.copy(letterSpacing = 1.sp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(unfocusedBorderColor = MaterialTheme.colors.primary)
                )
                Spacer(modifier = Modifier.width(10.dp))
                IconButton(
                    onClick = {
                        // first send the message
                        viewModel.addMessageToDatabase(
                            Message(
                                type = "string",
                                content = viewModel.enteredMessageState.value,
                                senderId = LoggedInUserData.loggedInUserId,
                                senderName = LoggedInUserData.loggedInUserName,
                                numberOfMembers = 2
                            ), chatRoomId = viewModel.chattingRoomId.value
                        )
                        // second update the chat room with the last message
                        viewModel.updateLastMessage(
                            message = Message(
                                content = viewModel.enteredMessageState.value,
                                type = "string"
                            ), chatRoomId = viewModel.chattingRoomId.value
                        )
                        // clear the text box
                        viewModel.onEnteredMessageStateChanges("")

                    }, modifier = Modifier
                        .background(MaterialTheme.colors.primary, shape = CircleShape)
                        .size(height = 60.dp, width = 60.dp)
                ) {
                    Icon(imageVector = Icons.Default.Send, contentDescription = "Send Message")
                }
            }
        }
    }
}