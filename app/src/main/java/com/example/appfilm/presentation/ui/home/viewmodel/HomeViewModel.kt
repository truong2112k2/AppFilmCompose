package com.example.appfilm.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.model.Movie
import com.example.appfilm.domain.usecase.AppUseCases
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

    private val appUseCases: AppUseCases
): ViewModel() {


    private val _logoutState = MutableStateFlow(HomeUIState())
    val logoutState : StateFlow<HomeUIState> = _logoutState


    fun logout(googleSignInClient: GoogleSignInClient){

        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.logoutUseCase.invoke(googleSignInClient).collect{ result ->

           _logoutState.value = when(result){
               is Resource.Loading -> {
                   Log.d(Constants.STATUS_TAG,"Loading logout")
                   HomeUIState(isLoading = true)
               }
               is Resource.Success -> {
                   Log.d(Constants.STATUS_TAG,"Logout Success")
                   HomeUIState(isSuccess =  true )
               }
               is Resource.Error -> {
                   Log.d(Constants.STATUS_TAG,"Logout Error")
                   HomeUIState(error =  result.message)

               }
           }
            }
        }

    }

    private val _getNewMovieState = MutableStateFlow(HomeUIState())
    val getNewMovieState : StateFlow<HomeUIState> = _getNewMovieState


    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies : StateFlow<List<Movie>> = _movies
    fun getNewMovie(page: Int ){
        _getNewMovieState.value = HomeUIState(isLoading = true)
        viewModelScope.launch(Dispatchers.IO) {
          val result =  appUseCases.getNewMoviesUseCase.getNewMovies(page)

            if(result.data?.isNotEmpty() == true){

                _movies.value = result.data
                _getNewMovieState.value = HomeUIState(isSuccess = true )

            }else{

                _getNewMovieState.value = HomeUIState(error = result.exception?.let {
                    convertGetMoviesException(it)
                })


            }
        }
    }

    private fun convertGetMoviesException(exception: Exception): String {
        return when (exception) {
            is IOException -> {
                "Cannot connect to the internet. Please check your network connection."
            }
            is HttpException -> {
                when (exception.code()) {
                    400 -> "Bad request. Please try again."
                    401 -> "Unauthorized access. Please log in again."
                    403 -> "Access denied."
                    404 -> "Requested content not found."
                    500 -> "Internal server error. Please try again later."
                    else -> "Server error: ${exception.code()} - ${exception.message()}"
                }
            }
            else -> {
                "An unexpected error occurred. Please try again."
            }
        }
    }



}