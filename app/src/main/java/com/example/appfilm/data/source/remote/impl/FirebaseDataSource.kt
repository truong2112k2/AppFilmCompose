package com.example.appfilm.data.source.remote.impl

import android.util.Log
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.data.source.remote.IFirebaseDataSource
import com.example.appfilm.data.source.remote.dto.movie_dto.Item
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class FirebaseDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : IFirebaseDataSource {

    override suspend fun login(email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        val user = firebaseAuth.currentUser

        if (user != null) {
            Log.d(
                Constants.STATUS_TAG,
                "Email just logged in ${user.email} - Verify: ${user.isEmailVerified}"
            )
        }

        try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val isVerified = firebaseAuth.currentUser?.isEmailVerified == true
            emit(Resource.Success(isVerified))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Login Failed", e))

        }


    }

    override suspend fun register(email: String, password: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = firebaseAuth.currentUser

            if (user != null) {
                Log.d(
                    Constants.STATUS_TAG,
                    "email just registered is ${user.email} - Verify: ${user.isEmailVerified}"
                )
            }

            firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            firebaseAuth.currentUser?.sendEmailVerification()?.await()


            emit(Resource.Success(Unit))
        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Register Failed", e))
        }
    }

    override suspend fun resendVerificationEmail(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = firebaseAuth.currentUser

            if (user != null && !user.isEmailVerified) {
                Log.d(Constants.STATUS_TAG, "Email just requested verify email ${user.email} ")

                user.sendEmailVerification().await()

                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("User not logged in or email verified"))

            }
        } catch (e: Exception) {

            emit(Resource.Error(e.message ?: "Resend email failed", e))
        }
    }

    override suspend fun resetPassword(email: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        try {

            FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
            emit(Resource.Success(Unit))

        } catch (e: Exception) {
            emit(Resource.Error(e.message ?: "Send email reset password failed", e))
        }
    }

    override suspend fun firebaseSignInWithGoogle(idToken: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading())

        val user = firebaseAuth.currentUser
        if (user != null) {
            Log.d(
                Constants.STATUS_TAG,
                "Email just logged in ${user.email} - Verify: ${user.isEmailVerified}"
            )
        }

        try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            firebaseAuth.signInWithCredential(credential).await()
            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error("Google sign-in failed: ${e.message}")) // lỗi đăng nhập
        }
    }

    override suspend fun firebaseLogOutAccount(googleSignInClient: GoogleSignInClient): Flow<Resource<Boolean>> =
        flow {
            emit(Resource.Loading())
            try {
                Log.d(
                    Constants.STATUS_TAG,
                    "Email just logged in ${firebaseAuth.currentUser?.email}"
                )
                firebaseAuth.signOut()
                googleSignInClient.signOut().await()
                emit(Resource.Success(true))
            } catch (e: Exception) {
                emit(Resource.Error("Google log-out failed: ${e.message}")) // lỗi đăng nhập

            }
        }

    override suspend fun checkUseLoginAndVerify(): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        try {
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null && user.isEmailVerified) {
                emit(Resource.Success(Unit))
            } else {
                emit(Resource.Error("Email not login"))

            }
        } catch (e: Exception) {
            emit(Resource.Error("Check log-in failed: ${e.message}")) // lỗi đăng nhập

        }
    }

    override suspend fun addFavoriteMovie(movie: Item): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            emit(Resource.Error("User is not logged in"))
            return@flow
        }

        val movieRef = firebaseDatabase.getReference("FAVORITE_MOVIES")
            .child(userId)
            .child(movie._id.toString())

        try {
            val snapshot = movieRef.get().await()
            if (snapshot.exists()) {
                emit(Resource.Error("Movie is already"))
            } else {
                movieRef.setValue(movie).await()
                emit(Resource.Success(Unit))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Add favorite movie failed: ${e.message}"))
        }
    }


    override suspend fun isFavorite(movieId: String): Resource<Boolean> {
        val userId = firebaseAuth.currentUser?.uid
        return try {
            val snapshot =
                firebaseDatabase.getReference("FAVORITE_MOVIES")
                    .child(userId.toString())
                    .child(movieId)
                    .get()
                    .await()

            Resource.Success(snapshot.exists())

        } catch (e: Exception) {
            Resource.Error("Check favourite movies failed: ${e.message}")
        }
    }

    override suspend fun getFavouriteMovies(): Flow<Resource<List<Item>>> = callbackFlow {
        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            trySend(Resource.Error("User not logged in"))
            close()
            return@callbackFlow
        }

        trySend(Resource.Loading())

        val ref = firebaseDatabase.getReference("FAVORITE_MOVIES").child(userId)

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = snapshot.children.mapNotNull {
                    it.getValue(Item::class.java)
                }
                trySend(Resource.Success(movies))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(Resource.Error("Database error: ${error.message}"))
            }
        }

        ref.addValueEventListener(listener)

        awaitClose {
            ref.removeEventListener(listener)
        }

    }

    override suspend fun removeFavoriteMovie(movieId: String): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())

        val userId = firebaseAuth.currentUser?.uid
        if (userId == null) {
            emit(Resource.Error("User is not logged in"))
            return@flow
        }

        val movieRef = firebaseDatabase.getReference("FAVORITE_MOVIES")
            .child(userId)
            .child(movieId)

        try {
            val snapshot = movieRef.get().await()
            if (!snapshot.exists()) {
                emit(Resource.Error("Movie does not exist in favorites"))
            } else {
                movieRef.removeValue().await()
                emit(Resource.Success(Unit))
            }
        } catch (e: Exception) {
            emit(Resource.Error("Remove favorite movie failed: ${e.message}"))
        }
    }

}