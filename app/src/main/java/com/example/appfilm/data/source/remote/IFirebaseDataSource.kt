package com.example.appfilm.data.source.remote

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.dto.movie_dto.MovieDto
import com.example.appfilm.domain.model.Movie
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow

interface IFirebaseDataSource {
    suspend fun login(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun register(email: String, password: String): Flow<Resource<Unit>>
    suspend fun resendVerificationEmail(): Flow<Resource<Unit>>
    suspend fun resetPassword(email: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>
    suspend fun firebaseLogOutAccount(  googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>>
    suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>>

    suspend fun addFavoriteMovie(movie: Movie):Flow<Resource<Unit>>
    suspend fun getFavoriteMovies(): Flow<Resource<List<MovieDto>>>
    suspend fun isFavorite(movieId: String): Resource<Boolean>
}