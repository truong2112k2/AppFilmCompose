package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow

interface IFirebaseDataSource {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun register(email: String, password: String): Flow<Resource<Unit>>
    suspend fun resendVerificationEmail(): Flow<Resource<Unit>>
    suspend fun resetPassword(email: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>
    suspend fun firebaseLogOutAccount(  googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>>
    suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>>
}