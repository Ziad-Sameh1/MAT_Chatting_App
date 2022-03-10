package com.example.matchatapp.feature_chatting.presentation.chat_list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.matchatapp.R
import com.example.matchatapp.Screen
import com.example.matchatapp.feature_chatting.presentation.chat_list.components.ChatList
import com.example.matchatapp.feature_chatting.presentation.chat_list.components.NoChatsFound
import com.example.matchatapp.feature_chatting.presentation.common.CommonErrorScreen
import com.example.matchatapp.ui.theme.CardGray
import com.example.matchatapp.utils.Constants
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.systemuicontroller.SystemUiController

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel,
    systemUiController: SystemUiController,
    navController: NavController
) {
    if (isSystemInDarkTheme(
        )
    ) {
        systemUiController.setStatusBarColor(
            color = CardGray
        )
    } else {
        systemUiController.setStatusBarColor(
            color = MaterialTheme.colors.primary
        )
    }
    Scaffold(
        topBar = {
            // TODO: App Top bar
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        fontSize = 26.sp,
                        modifier = Modifier.padding(top = 5.dp),
                        letterSpacing = 1.sp, color = Color.White
                    )
                },
                navigationIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.app_icon),
                        contentDescription = ""
                    )
                }, elevation = 10.dp, actions = {
                    Row() {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(imageVector = Icons.Default.Search, contentDescription = "search")
                        }
                        IconButton(onClick = {
                            navController.navigate(Screen.AddGroupScreen.route) {
                                popUpTo(
                                    Screen.ChatListScreen.route
                                )
                            }
                        }) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_add_group),
                                contentDescription = "add group"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.onSettingsDropDownMenuExpandedStateChanges(newValue = true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Settings,
                                contentDescription = "settings"
                            )
                        }
                    }
                }
            )
        },

        content = {
            // TODO: App Content
            if (viewModel.isSettingsDropDownMenuExpanded.value) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(align = Alignment.TopEnd)
                ) {
                    DropdownMenu(
                        expanded = viewModel.isSettingsDropDownMenuExpanded.value,
                        onDismissRequest = {
                            viewModel.onSettingsDropDownMenuExpandedStateChanges(
                                newValue = false
                            )
                        }
                    ) {
                        Column {
                            DropdownMenuItem(onClick = {
                                viewModel.onSettingsDropDownMenuExpandedStateChanges(false)
                                navController.navigate(
                                    Screen.ProfileScreen.route
                                ) {
                                    popUpTo(Screen.ChatListScreen.route)
                                }
                            }) {
                                Text(
                                    text = stringResource(id = R.string.profile),
                                )
                            }
                        }
                    }
                }
            }
            if (viewModel.isLoadingState.value) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else if (viewModel.isErrorState.value) CommonErrorScreen()
            else {
                if (viewModel.chatRooms.value.isEmpty()) {
                    NoChatsFound()
                } else {
                    ChatList(
                        chatRooms = viewModel.chatRooms.value,
                        viewModel = viewModel,
                        navController = navController
                    )
                }
            }
        }
    )
}