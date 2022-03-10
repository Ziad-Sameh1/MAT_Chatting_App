package com.example.matchatapp.feature_chatting.domain.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

data class Message(
    val type: String? = null,
    val content: String? = null,
    @ServerTimestamp
    val timeSent: Date? = null,
    val timeSeen: HashMap<String, String>? = null,
    val senderId: String? = null,
    val senderName: String? = null,
    val numberOfMembers: Int? = null
)
