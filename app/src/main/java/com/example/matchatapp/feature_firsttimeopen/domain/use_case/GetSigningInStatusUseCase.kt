package com.example.matchatapp.feature_firsttimeopen.domain.use_case

import com.example.matchatapp.feature_firsttimeopen.domain.repository.AuthRepo
import javax.inject.Inject

class GetSigningInStatusUseCase @Inject constructor(
    private val repo: AuthRepo
){
    operator fun invoke() : String {
        return repo.signingInStatus()
    }
}