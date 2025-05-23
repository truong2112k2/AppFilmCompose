package com.example.appfilm.presentation.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidUserException

fun convertSendEmailException(e: Exception?, fallback: String? = null): String {
    return when (e) {
        is FirebaseAuthInvalidUserException -> "User does not exist"
        is FirebaseNetworkException -> "Network connection error"
        is FirebaseAuthException -> "Authentication error: ${e.message}"
        else -> fallback ?: e?.message ?: "An unknown error occurred"
    }
}

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

    return activeNetwork.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)


}

