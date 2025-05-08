package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import kotlinx.coroutines.flow.Flow

interface IAuthDataSource {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun register(email: String, password: String): Flow<Resource<Unit>>
    suspend fun resendVerificationEmail(): Flow<Resource<Unit>>
    suspend fun resetPassword(email: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>

}