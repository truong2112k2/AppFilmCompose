package com.example.appfilm.domain.usecase.firebase.authentication

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RegisterUseCase @Inject constructor(
    private val firebaseRepository: IFirebase
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> {
           return firebaseRepository.firebaseRegister(email, password)
    }
}