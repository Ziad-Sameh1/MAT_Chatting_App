package com.example.matchatapp.feature_chatting.di

import android.content.Context
import com.example.matchatapp.feature_chatting.data.repository.CloudMessagesRepoImpl
import com.example.matchatapp.feature_chatting.data.repository.ContactsRepoImpl
import com.example.matchatapp.feature_chatting.data.repository.DatabaseRepoImpl
import com.example.matchatapp.feature_chatting.data.repository.StorageRepoImpl
import com.example.matchatapp.feature_chatting.domain.repository.CloudMessagesRepo
import com.example.matchatapp.feature_chatting.domain.repository.ContactsRepo
import com.example.matchatapp.feature_chatting.domain.repository.DatabaseRepo
import com.example.matchatapp.feature_chatting.domain.repository.StorageRepo
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class ChattingModule {


    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    @Singleton
    fun provideDatabaseRepo(
        firebaseFirestore: FirebaseFirestore
    ): DatabaseRepo {
        return DatabaseRepoImpl(
            firebaseFirestore = firebaseFirestore
        )
    }

    @Provides
    @Singleton
    fun provideContactsRepo(@ApplicationContext context: Context): ContactsRepo {
        return ContactsRepoImpl(context = context)
    }

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage {
        return FirebaseStorage.getInstance()
    }

    @Provides
    @Singleton
    fun provideStorageReference(firebaseStorage: FirebaseStorage): StorageReference {
        return firebaseStorage.reference
    }

    @Provides
    @Singleton
    fun provideStorageRepo(
        storageReference: StorageReference,
        @ApplicationContext context: Context
    ): StorageRepo {
        return StorageRepoImpl(storageReference = storageReference, context = context)
    }

    @Singleton
    @Provides
    fun provideFirebaseMessaging(): FirebaseMessaging {
        return FirebaseMessaging.getInstance()
    }

    @Singleton
    @Provides
    fun provideCloudMessagingRepo(firebaseMessaging: FirebaseMessaging): CloudMessagesRepo {
        return CloudMessagesRepoImpl(firebaseMessaging = firebaseMessaging)
    }

}