package com.example.appfilm.domain.usecase

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebaseRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LogoutUseCase @Inject constructor(
    private val firebaseRepository: IFirebaseRepository
){

    suspend operator fun invoke(googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>>{
       return firebaseRepository.firebaseLogOutAccount(googleSignInClient)
    }

}