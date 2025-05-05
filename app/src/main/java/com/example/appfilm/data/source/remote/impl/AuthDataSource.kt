package com.example.appfilm.data.source.remote.impl

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IAuthDataSource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


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
            emit(Resource.Error(e.message ?: "Login Failed"))
        }
    }

    override fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
       emit(Resource.Loading())
        try{
            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            emit(Resource.Success(Unit))
        }catch (e: Exception){
            emit(Resource.Error(e.message ?: "Register Failed"))
        }
    }
}