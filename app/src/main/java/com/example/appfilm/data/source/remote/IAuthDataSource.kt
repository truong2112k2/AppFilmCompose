package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthDataSource {
    fun login(email: String, password: String): Flow<Resource<Boolean>>
    fun register(email: String, password: String): Flow<Resource<Unit>>
    fun resendVerificationEmail(): Flow<Resource<Unit>>
}