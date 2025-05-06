package com.example.appfilm.domain.repository

import com.example.appfilm.common.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    fun login(email: String, password: String): Flow<Resource<Boolean>>
    fun register(email: String, password: String): Flow<Resource<Unit>>
    fun resendVerificationEmail(): Flow<Resource<Unit>>
}
