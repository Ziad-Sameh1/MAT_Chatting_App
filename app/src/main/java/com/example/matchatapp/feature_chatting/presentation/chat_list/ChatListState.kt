package com.example.matchatapp.feature_chatting.presentation.chat_list

import com.example.matchatapp.feature_chatting.domain.model.ChatRoom

data class ChatListState(
    var isLoading: Boolean = true,
    var isError: Boolean = false,
    var chatRooms: List<ChatRoom> = emptyList()
)
