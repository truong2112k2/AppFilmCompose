package com.example.appfilm.domain.usecase.firebase.authentication

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LogInWithoutPassUseCase @Inject constructor(
    private val firebaseRepository: IFirebase

) {
    suspend operator fun invoke(idToken: String): Flow<Resource<Boolean>> {
        return firebaseRepository.firebaseSignInWithGoogle(idToken)
    }
}