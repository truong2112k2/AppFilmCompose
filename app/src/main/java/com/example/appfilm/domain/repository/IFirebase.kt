package com.example.appfilm.domain.repository

import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow

interface IFirebase {
    suspend fun firebaseLogin(email: String, password: String): Flow<Resource<Boolean>>
    suspend fun firebaseRegister(email: String, password: String): Flow<Resource<Unit>>
    suspend fun firebaseSendVerification(): Flow<Resource<Unit>>
    suspend fun firebaseResetPassword(email: String): Flow<Resource<Unit>>
    suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>>
    suspend fun firebaseLogOutAccount(  googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>>
    suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>>


    suspend fun addFavoriteMovie(movie: Movie):Flow<Resource<Unit>>
    suspend fun isFavorite(movieId: String): Resource<Boolean>
    suspend fun getFavouriteMovies(): Flow<Resource<List<Movie>>>
    suspend fun removeFavoriteMovie(movieId: String): Flow<Resource<Unit>>

}
