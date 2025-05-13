package com.example.appfilm.presentation.ui.home.viewmodel

import android.content.Context
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





}