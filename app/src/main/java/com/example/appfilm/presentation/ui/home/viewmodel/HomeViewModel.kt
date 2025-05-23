package com.example.appfilm.presentation.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appfilm.common.Constants
import com.example.appfilm.common.Resource
import com.example.appfilm.domain.usecase.AppUseCases
import com.example.appfilm.presentation.ui.UIState
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(

    private val appUseCases: AppUseCases
) : ViewModel() {


    private val _logoutState = MutableStateFlow(UIState())
    val logoutState: StateFlow<UIState> = _logoutState
    private fun logout(googleSignInClient: GoogleSignInClient) {

        viewModelScope.launch(Dispatchers.IO) {
            appUseCases.logoutUseCase.invoke(googleSignInClient).collect { result ->

                _logoutState.value = when (result) {
                    is Resource.Loading -> {
                        Log.d(Constants.STATUS_TAG, "Loading logout")
                        UIState(isLoading = true)
                    }

                    is Resource.Success -> {
                        Log.d(Constants.STATUS_TAG, "Logout Success")
                        UIState(isSuccess = true)
                    }

                    is Resource.Error -> {
                        Log.d(Constants.STATUS_TAG, "Logout Error")
                        UIState(error = result.message)

                    }
                }
            }
        }

    }

    fun handleEvent(homeEvent: HomeEvent) {
        when (homeEvent) {
            is HomeEvent.Logout -> {
                logout(homeEvent.googleSignInClient)
            }

        }
    }


}