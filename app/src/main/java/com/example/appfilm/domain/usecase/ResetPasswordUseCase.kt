package com.example.appfilm.domain.usecase

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResetPasswordUseCase @Inject constructor(
    private val authRepository: IAuthRepository

) {

    suspend operator fun invoke(email: String): Flow<Resource<Unit>> {
        return authRepository.resetPassword(email)

    }
}