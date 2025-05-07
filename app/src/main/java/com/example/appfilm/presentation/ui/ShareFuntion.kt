package com.example.appfilm.presentation.ui

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