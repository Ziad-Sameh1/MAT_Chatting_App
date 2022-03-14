package com.example.matchatapp.feature_chatting.presentation.chat_list.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.presentation.chat_list.ChatListViewModel
import com.example.matchatapp.feature_chatting.presentation.chatting_room.ChattingRoomViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatList(
    chatRooms: List<ChatRoom>,
    viewModel: ChatListViewModel,
    navController: NavController,
    chattingRoomViewModel: ChattingRoomViewModel
) {
    LazyColumn {
//        items(1) {
//            ChatActiveList(chatItemDataClass = ChatRoom())
//        }
        items(items = chatRooms) { chatRoom ->
            ChatItem(
                chatRoom = chatRoom,
                viewModel = viewModel,
                navController = navController,
                chattingRoomViewModel = chattingRoomViewModel
            )
        }
    }
}