package com.example.matchatapp.feature_chatting.presentation.add_group_screen.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.matchatapp.feature_chatting.presentation.add_group_screen.AddGroupViewModel
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel

@Composable
fun UsersListComposable(
    viewModel: AddGroupViewModel,
    navController: NavController
) {
    LazyColumn {
        items(viewModel.matUsers) { user ->
            UserItemComposable(
                user = user,
                navController = navController,
                viewModel = viewModel
            )
        }
    }
}