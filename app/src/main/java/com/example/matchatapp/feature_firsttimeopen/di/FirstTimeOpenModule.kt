package com.example.matchatapp.feature_firsttimeopen.di

import androidx.activity.ComponentActivity
import com.example.matchatapp.feature_firsttimeopen.data.repository.AuthRepoImpl
import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirstTimeOpenModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Singleton
    @Provides
    fun provideAuthRepo(firebaseAuth: FirebaseAuth): AuthRepo {
        return AuthRepoImpl(firebaseAuth = firebaseAuth)
    }


}