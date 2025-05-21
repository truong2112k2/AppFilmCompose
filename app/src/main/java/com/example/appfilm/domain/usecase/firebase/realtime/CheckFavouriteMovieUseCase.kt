package com.example.appfilm.domain.usecase.firebase.realtime

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.repository.IFirebase
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CheckFavouriteMovieUseCase @Inject constructor(
    private val firebaseRepository: IFirebase

) {

    suspend operator fun invoke(movieId: String): Resource<Boolean> {
        return firebaseRepository.isFavorite(movieId)
    }
}