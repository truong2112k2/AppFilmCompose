package com.example.appfilm.domain.usecase

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebaseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LogInUseCase @Inject constructor(
  private val firebaseRepository: IFirebaseRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Boolean>>{
        return firebaseRepository.firebaseLogin(email, password)
    }
}