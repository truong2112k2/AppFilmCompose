package com.example.appfilm.domain.usecase.firebase.realtime

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IFirebase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AddFavouriteMovieUseCase @Inject constructor (
    private val firebaseRepository: IFirebase

){
    /*
    override suspend fun addFavoriteMovie(movie: Movie): Flow<Resource<Unit>> {
        return firebaseDataSource.addFavoriteMovie(movie)
    }
     */
    suspend operator fun invoke (movie: Movie): Flow<Resource<Unit>> {
        return firebaseRepository.addFavoriteMovie(movie)
    }
}