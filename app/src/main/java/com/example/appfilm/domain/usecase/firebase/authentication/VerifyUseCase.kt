package com.example.appfilm.domain.usecase.firebase.authentication

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class VerifyUseCase @Inject constructor(
    private val firebaseRepository: IFirebase
) {

    suspend operator fun invoke() : Flow<Resource<Unit>> {
        return firebaseRepository.firebaseSendVerification()
    }

}