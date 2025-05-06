package com.example.appfilm.data.source.remote.impl

import android.util.Log
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IAuthDataSource
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthRecentLoginRequiredException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Suppress("KotlinConstantConditions")
@Singleton
class AuthDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth
): IAuthDataSource {

    override fun login(email: String, password: String): Flow<Resource<Unit>> = flow {

        emit(Resource.Loading())
        try{
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Resource.Success(Unit))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Login Failed", e))

        }
    }

    override fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()


            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Register Failed", e))
        }
    }

    override fun resendVerificationEmail(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try{
            val user = firebaseAuth.currentUser

            if(user != null  && !user.isEmailVerified){
                Log.d("CheckUser", user.email.toString() +":" +user.isEmailVerified.toString())

                user.sendEmailVerification().await()

                emit(Resource.Success(Unit))
            }else{
                emit(Resource.Error("User not logged in or email verified"))

            }
        }catch (e: Exception){

            emit(Resource.Error(e.message ?: "Resend email failed", e))
        }
    }
}