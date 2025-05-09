package com.example.appfilm.data.source.remote.impl

import android.util.Log
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : IFirebaseDataSource {

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        val user = firebaseAuth.currentUser

        if (user != null) {
            Log.d(
                Constants.STATUS_TAG,
                "Email just logged in ${user.email} - Verify: ${user.isEmailVerified.toString()}"
            )
        }
        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val isVerified = firebaseAuth.currentUser?.isEmailVerified == true
            emit(Resource.Success(isVerified))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Login Failed", e))

        }
    }

    override suspend fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = firebaseAuth.currentUser

            if (user != null) {
                Log.d(
                    Constants.STATUS_TAG,
                    "email just registered is ${user.email} - Verify: ${user.isEmailVerified.toString()}"
                )
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()


            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Register Failed", e))
        }
    }

    override suspend fun resendVerificationEmail(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = firebaseAuth.currentUser

            if (user != null && !user.isEmailVerified) {
                Log.d(Constants.STATUS_TAG, "Email just requested verify email ${user.email} ")

                user.sendEmailVerification().await()

                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("User not logged in or email verified"))

            }
        } catch (e: Exception) {

            emit(Resource.Error(e.message ?: "Resend email failed", e))
        }
    }

    override suspend fun resetPassword(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Send email reset password failed", e))
        }
    }

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        val user = firebaseAuth.currentUser
        if (user != null) {
            Log.d(
                Constants.STATUS_TAG,
                "Email just logged in ${user.email} - Verify: ${user.isEmailVerified.toString()}"
            )
        }

        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("Google sign-in failed: ${e.message}")) // lỗi đăng nhập
        }
    }

    override suspend fun firebaseLogOutAccount(googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                Log.d(
                    Constants.STATUS_TAG,
                    "Email just logged in ${firebaseAuth.currentUser?.email}"
                )
                firebaseAuth.signOut()
                googleSignInClient.signOut().await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("Google log-out failed: ${e.message}")) // lỗi đăng nhập

            }
        }

    override suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null && user.isEmailVerified) {
              emit( Resource.Success(Unit))
            } else {
                emit(    Resource.Error("Email not login"))

            }
        }catch (e: Exception){
            emit(Resource.Error("Check log-in failed: ${e.message}")) // lỗi đăng nhập

        }
    }



}