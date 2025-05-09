package com.example.appfilm.domain.repository

import com.example.appfilm.common.Resource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow

interface IFirebaseRepository {
    suspend fun firebaseLogin(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun firebaseRegister(email: String, password: String): Flow<Resource<Unit>>
    suspend fun firebaseSendVerification(): Flow<Resource<Unit>>
    suspend fun firebaseResetPassword(email: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>
    suspend fun firebaseLogOutAccount(  googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>>
    suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>>
}
