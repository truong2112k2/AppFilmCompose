package com.example.appfilm.domain.usecase

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LogInWithoutPassUseCase @Inject constructor(
    private val authRepository: IAuthRepository

) {

    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>{
        return authRepository.firebaseSignInWithGoogle(idToken)
    }
}