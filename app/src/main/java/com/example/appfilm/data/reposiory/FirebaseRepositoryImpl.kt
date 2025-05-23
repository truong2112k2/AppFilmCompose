package com.example.appfilm.data.reposiory

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IFirebase
import com.example.appfilm.domain.toItem
import com.example.appfilm.domain.toMovie
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseRepositoryImpl @Inject constructor(
    private val firebaseDataSource: IFirebaseDataSource
) : IFirebase {
    override suspend fun firebaseLogin(email: String, password: String): Flow<Resource<Boolean>> {
        return firebaseDataSource.login(email, password)
    }

    override suspend fun firebaseRegister(email: String, password: String): Flow<Resource<Unit>> {
        return firebaseDataSource.register(email, password)
    }

    override suspend fun firebaseSendVerification(): Flow<Resource<Unit>> {
        return firebaseDataSource.resendVerificationEmail()
    }

    override suspend fun firebaseResetPassword(email: String): Flow<Resource<Unit>> {
        return firebaseDataSource.resetPassword(email)
    }

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>> {
        return firebaseDataSource.firebaseSignInWithGoogle(idToken)
    }

    override suspend fun firebaseLogOutAccount(googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>> {
        return firebaseDataSource.firebaseLogOutAccount(googleSignInClient)
    }

    override suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>> {
        return firebaseDataSource.checkUseLoginAndVerify()
    }

    override suspend fun addFavoriteMovie(movie: Movie): Flow<Resource<Unit>> {

        return firebaseDataSource.addFavoriteMovie(movie.toItem())
    }


    override suspend fun isFavorite(movieId: String): Resource<Boolean> {
        return firebaseDataSource.isFavorite(movieId)
    }

    override suspend fun getFavouriteMovies(): Flow<Resource<List<Movie>>> {
        return firebaseDataSource.getFavouriteMovies()
            .map { result ->
                when (result) {
                    is Resource.Loading -> Resource.Loading()
                    is Resource.Error -> Resource.Error(result.message ?: "Unknown error")
                    is Resource.Success -> {
                        val data = result.data?.map { it.toMovie() } ?: emptyList()
                        Resource.Success(data)
                    }
                }
            }
    }

    override suspend fun removeFavoriteMovie(movieId: String): Flow<Resource<Unit>> {
        return firebaseDataSource.removeFavoriteMovie(movieId)
    }

    override suspend fun isFavoriteNewMovies(movieId: String): Flow<Resource<Boolean>> {
        return firebaseDataSource.isFavoriteNewMovies(movieId)
    }
}