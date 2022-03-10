package com.example.matchatapp.feature_chatting.domain.model

import android.os.Parcelable
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import java.util.*

@Parcelize
data class ChatRoom(
    val chatRoomId: String? = null,
    val userName: List<String> = emptyList(),
    val userId: List<String> = emptyList(),
    val userProfilePic: List<String> = emptyList(),
    val numberOfParticipants: Int = 2,
    val numberOfUnreadMessages: HashMap<String, Int> = HashMap<String, Int>(mutableMapOf()),
    val lastMessageType: String? = null,
    val lastMessageContent: String? = null,
    val lastMessageStatus: HashMap<String, Boolean>? = null,
    @ServerTimestamp
    val lastActivity: Date? = null
) : Parcelable
