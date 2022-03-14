package com.example.matchatapp.feature_chatting.data.repository

import android.util.Log
import com.example.matchatapp.LoggedInUserData
import com.example.matchatapp.feature_chatting.data.interfaces.*
import com.example.matchatapp.feature_chatting.domain.model.ChatRoom
import com.example.matchatapp.feature_chatting.domain.model.Message
import com.example.matchatapp.feature_chatting.domain.model.Response
import com.example.matchatapp.feature_chatting.domain.model.User
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import com.example.matchatapp.utils.Constants.TAG
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class DatabaseRepoImpl @Inject constructor(
    private val firebaseFirestore: FirebaseFirestore
) : DatabaseRepo {
    private val mainCollectionReference = firebaseFirestore.collection("chat_rooms_main_collection")
    private val usersCollectionReference = firebaseFirestore.collection("users")
    private val messagesSubCollection = "messages"

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserChats(userId: String) = callbackFlow {
        val snapshotListener = mainCollectionReference.whereArrayContains("userId", userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) Log.i(TAG, "Listening Failed due to exception: ${e.message}")
                if (snapshot == null) return@addSnapshotListener
                Log.i(TAG, "getUserChats: ${snapshot.documents}")
                val chatRooms = snapshot.toObjects(ChatRoom::class.java)
                trySend(Response.Success(chatRooms))
            }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun addOrUpdateUserData(
        user: User,
        addOrUpdateUserResultListener: AddDataToDatabaseResultListener
    ) {
        usersCollectionReference.document(user.userId).set(user, SetOptions.merge())
            .addOnSuccessListener {
                addOrUpdateUserResultListener.onSuccess(true)
            }.addOnFailureListener {
                addOrUpdateUserResultListener.onFailure(it)
            }
    }

    override fun getUserPersonalInfo(
        userPhoneNumber: String,
        getUserPersonalInfoResultListener: GetUserPersonalInfoResultListener
    ) {
        usersCollectionReference.whereEqualTo("userId", userPhoneNumber)
            .get().addOnSuccessListener { documents ->
                if (documents != null) {
                    for (document in documents) {
                        val userData = document.data
                        Log.i(TAG, "getUserPersonalInfo: $userData")
                        LoggedInUserData.loggedInUserId = document.data["userId"].toString()
                        LoggedInUserData.loggedInUserName = document.data["userName"].toString()
                        LoggedInUserData.loggedInUserBio = document.data["userBio"].toString()
                        LoggedInUserData.loggedInUserPhoneNumber =
                            document.data["userPhone"].toString()
                        LoggedInUserData.loggedInUserprofilePicUri =
                            document.data["userProfilePic"].toString()
                        getUserPersonalInfoResultListener.onSuccess()
                    }
                } else {
                    Log.i(TAG, "getUserPersonalInfo: user not found")
                }
            }.addOnFailureListener {
                Log.i(TAG, "getUserPersonalInfo: Failure Occurred when trying to get user data")
            }
    }

    override fun isMatUser(
        userPhoneNumber: String,
        isMatUserResultListener: IsMatUserResultListener
    ) {
        usersCollectionReference.whereEqualTo("userId", userPhoneNumber).limit(1).get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Log.i(TAG, "isMatUser: User not found")
                    isMatUserResultListener.onSuccess(User(userId = "", userPhone = ""))
                } else {
                    Log.i(TAG, "isMatUser: User Found")
                    isMatUserResultListener.onSuccess(
                        User(
                            userId = it.documents[0].data?.get("userId")
                                .toString(),
                            userName = it.documents[0].data?.get("userName").toString(),
                            userBio = it.documents[0].data?.get("userBio").toString(),
                            userPhone = it.documents[0].data?.get("userPhone").toString(),
                            userProfilePic = it.documents[0].data?.get("userProfilePic").toString()
                        )
                    )
                }
            }.addOnFailureListener {
                Log.i(TAG, "isMatUser: Error occurred during searching for user")
                isMatUserResultListener.onFailure(it)
            }
    }

    override fun createChatRoom(
        chatRoom: ChatRoom,
        createChatRoomResultListener: CreateChatRoomResultListener
    ) {
        val chatRoomId = chatRoom.userId[0] + "_" + chatRoom.userId[1]
        Log.i(TAG, "createChatRoom: Chat room is created")
        mainCollectionReference.document(chatRoomId).set(chatRoom)
            .addOnSuccessListener {
                Log.i(TAG, "createChatRoom: Chat Room Created Successfully")
                createChatRoomResultListener.onSuccess()
            }
            .addOnFailureListener {
                Log.i(TAG, "createChatRoom: Cannot Create Chat Room")
                createChatRoomResultListener.onFailure(it)
            }
    }

    override fun addMessageToChatRoom(
        chatRoomId: String,
        message: Message,
        addMessageToChatRoom: AddMessageToChatRoomResultListener
    ) {
        mainCollectionReference.document(chatRoomId).collection(messagesSubCollection).document()
            .set(message).addOnSuccessListener {
                addMessageToChatRoom.onSuccess()
                Log.i(TAG, "addMessageToChatRoom: Message Added Successfully")
            }.addOnFailureListener {
                addMessageToChatRoom.onFailure(it)
            }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun readChatRoomMessages(
        chatRoomId: String
    ) = callbackFlow {
        val snapshotListener =
            mainCollectionReference.document(chatRoomId).collection(messagesSubCollection)
                .orderBy("timeSent", Query.Direction.DESCENDING)
                .addSnapshotListener { snapshot, e ->
                    if (e != null) Log.i(
                        TAG,
                        "readChatRoomMessages: Listening to messages Failed -> error : $e"
                    )
                    if (snapshot == null) return@addSnapshotListener
                    val messages = snapshot.toObjects(Message::class.java)
                    Log.i(TAG, "readChatRoomMessages: in repo ${snapshot.documents}")
                    trySend(Response.Success(messages)).isSuccess
                }
        awaitClose {
            snapshotListener.remove()
        }
    }

    override fun checkIfChatRoomAvailable(
        firstUserPhoneNumber: String,
        secondUserPhoneNumber: String,
        checkIfChatRoomCreatedBeforeResultListener: CheckIfChatRoomCreatedBeforeResultListener
    ) {
        val firstCase = firstUserPhoneNumber + "_" + secondUserPhoneNumber
        val secondCase = secondUserPhoneNumber + "_" + firstUserPhoneNumber
        mainCollectionReference.whereIn("chatRoomId", listOf(firstCase, secondCase)).get()
            .addOnSuccessListener { documents ->
                if (documents.documents.isEmpty()) {
                    checkIfChatRoomCreatedBeforeResultListener.onNotFound()
                } else {
                    if (documents.documents[0].data?.get("chatRoomId") == firstCase) {
                        checkIfChatRoomCreatedBeforeResultListener.onSuccess(firstCase)
                    } else if (documents.documents[0].data?.get("chatRoomId") == secondCase) {
                        checkIfChatRoomCreatedBeforeResultListener.onSuccess(secondCase)
                    }
                }
            }
    }

    override fun updateLastMessage(message: Message, chatRoomId: String) {
        mainCollectionReference.document(chatRoomId).set(
            ChatRoom(
                lastMessageType = message.type,
                lastMessageContent = message.content,
                lastActivity = message.timeSent
            ), SetOptions.mergeFields("lastMessageType", "lastMessageContent")
        )
    }
}
