package com.example.appfilm.data.reposiory

import android.provider.Settings.Global.getString
import androidx.compose.ui.res.stringResource
import com.example.appfilm.R
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
    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> {
        return authDataSource.login(email, password)
    }

    override suspend fun register(email: String, password: String): Flow<Resource<Unit>> {


        return authDataSource.register(email, password)
    }

    override suspend fun resendVerificationEmail(): Flow<Resource<Unit>> {
        return authDataSource.resendVerificationEmail()
    }

    override suspend fun resetPassword(email: String): Flow<Resource<Unit>> {
        return authDataSource.resetPassword(email)
    }

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>> {
        return authDataSource.firebaseSignInWithGoogle(idToken)
    }
}