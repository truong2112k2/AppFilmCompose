package com.example.appfilm.data.reposiory

import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.repository.IFirebase
import com.example.appfilm.domain.toMovie
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
        return firebaseDataSource.addFavoriteMovie(movie)
    }


    override suspend fun getFavoriteMovies(): Flow<Resource<List<Movie>>> = flow {

        firebaseDataSource.getFavoriteMovies().collect { result ->
            when (result) {
                is Resource.Success -> {
                    result.data?.map { movieDto ->
                        val list = movieDto.items.map { item ->
                            item.toMovie()
                        }

                        emit(Resource.Success(list))
                    }

                }

                is Resource.Error -> {
                    emit(Resource.Error(result.message.toString()))
                }

                is Resource.Loading -> {
                    emit(Resource.Loading())
                }
            }

        }


    }

    override suspend fun isFavorite(movieId: String): Resource<Boolean> {
        return firebaseDataSource.isFavorite(movieId)
    }
}