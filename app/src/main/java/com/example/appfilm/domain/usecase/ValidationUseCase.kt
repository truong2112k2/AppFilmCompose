package com.example.appfilm.domain.usecase

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ValidationUseCase @Inject constructor(

) {
    fun validationPassword(password: String, passWordSecond: String): String? {
        if (password.isEmpty()) {
            return "Password can not is empty"
        }
        if (password.length < 6) {
            return "Weak password (minimum 6 characters)"
        }
        if (!password.any { it.isLetter() }) {
            return "Password must contain letters"
        }
        if (!password.any { it.isDigit() }) {
            return "Password must contain number"
        }

        if (password != passWordSecond) {
            return "Password not match"
        }

        return null
    }


    fun validationEmail(email: String): String? {
        if(email.isEmpty()){
            return "Email can not is empty"
        }
        if( !isValidEmail(email)){
            return "Invalid email address"

        }
        if(containsUpperCase(email)){
            return "Email must not contain uppercase letters"
        }
       return null
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.(com|vn|org|net)$")
        return email.matches(emailPattern)
    }

    private fun containsUpperCase(input: String): Boolean {
        return input.any { it.isUpperCase() }
    }

}