package com.example.appfilm.data.reposiory

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IAuthDataSource
import com.example.appfilm.domain.repository.IAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: IAuthDataSource
): IAuthRepository{
    override fun login(email: String, password: String): Flow<Resource<Unit>> {
        return authDataSource.login(email, password)
    }

    override fun register(email: String, password: String): Flow<Resource<Unit>> {
        return authDataSource.register(email, password)
    }

    override fun resendVerificationEmail(): Flow<Resource<Unit>> {
        return authDataSource.resendVerificationEmail()
    }
}