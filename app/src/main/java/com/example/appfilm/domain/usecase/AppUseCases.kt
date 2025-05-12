package com.example.appfilm.domain.usecase

import com.example.appfilm.domain.usecase.api_movie.GetNewMoviesUseCase
import com.example.appfilm.domain.usecase.firebase.CheckLoginUseCase
import com.example.appfilm.domain.usecase.firebase.LogInUseCase
import com.example.appfilm.domain.usecase.firebase.LogInWithoutPassUseCase
import com.example.appfilm.domain.usecase.firebase.LogoutUseCase
import com.example.appfilm.domain.usecase.firebase.RegisterUseCase
import com.example.appfilm.domain.usecase.firebase.ResetPassUseCase
import com.example.appfilm.domain.usecase.firebase.VerifyUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
data class AppUseCases @Inject constructor (
    val logInUseCase: LogInUseCase,
    val registerUseCase: RegisterUseCase,
    val validationUseCase: ValidationUseCase,
    val sendEmailVerificationUseCase: VerifyUseCase,
    val resetPassWordUseCase: ResetPassUseCase,
    val logInWithoutPassUseCase: LogInWithoutPassUseCase,
    val logoutUseCase: LogoutUseCase,
    val checkLoginUseCase: CheckLoginUseCase,
    val getNewMoviesUseCase: GetNewMoviesUseCase
) {
}