package com.example.matchatapp.feature_firsttimeopen.domain.use_case

import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import com.example.matchatapp.feature_firsttimeopen.util.Resource
import com.google.firebase.auth.FirebaseUser
import java.lang.Exception
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(
    private val repo: AuthRepo
) {
    operator fun invoke() : FirebaseUser?{
        return repo.signedInUserData()
    }
}