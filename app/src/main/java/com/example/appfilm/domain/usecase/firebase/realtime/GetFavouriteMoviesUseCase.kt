package com.example.appfilm.domain.usecase.firebase.realtime

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetFavouriteMoviesUseCase @Inject constructor(
    private val firebaseRepository: IFirebase
) {


    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        return firebaseRepository.getFavouriteMovies()
    }
}