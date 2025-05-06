package com.example.appfilm.domain.usecase

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ReSendEmailVerificationUseCase @Inject constructor(
    private val authDataSource: IAuthRepository
) {

    operator fun invoke() : Flow<Resource<Unit>> {
        return authDataSource.resendVerificationEmail()
    }

}