package com.example.appfilm.data.reposiory

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.domain.repository.IFirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: IFirebaseDataSource
): IFirebaseRepository{
    override suspend fun firebaseLogin(email: String, password: String): Flow<Resource<Boolean>> {
        return firebaseDataSource.login(email, password)
    }

    override suspend fun firebaseRegister(email: String, password: String): Flow<Resource<Unit>> {
        return firebaseDataSource.register(email, password)
    }

    override suspend fun firebaseSendVerification(): Flow<Resource<Unit>> {
        return firebaseDataSource.resendVerificationEmail()
    }

    override suspend fun firebaseResetPassword(email: String): Flow<Resource<Unit>> {
        return firebaseDataSource.resetPassword(email)
    }

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>> {
        return firebaseDataSource.firebaseSignInWithGoogle(idToken)
    }

    override suspend fun firebaseLogOutAccount(googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>> {
        return firebaseDataSource.firebaseLogOutAccount(googleSignInClient)
    }

    override suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>> {
        return firebaseDataSource.checkUseLoginAndVerify()
    }
}