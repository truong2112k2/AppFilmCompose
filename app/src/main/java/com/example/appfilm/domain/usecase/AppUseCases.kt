package com.example.appfilm.domain.usecase

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
    val checkLoginUseCase: CheckLoginUseCase
) {
}