package com.example.appfilm.domain.usecase

import com.example.appfilm.domain.usecase.api_movie.FetchCategoryUseCase
import com.example.appfilm.domain.usecase.api_movie.FetchDetailMovieUseCase
import com.example.appfilm.domain.usecase.api_movie.FetchMovieAndSaveUseCase
import com.example.appfilm.domain.usecase.api_movie.FetchMoviesByCategoryUseCase
import com.example.appfilm.domain.usecase.database.GetMoviesUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.CheckLoginUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.LogInUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.LogInWithoutPassUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.LogoutUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.RegisterUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.ResetPassUseCase
import com.example.appfilm.domain.usecase.firebase.authentication.VerifyUseCase
import com.example.appfilm.domain.usecase.firebase.realtime.AddFavouriteMovieUseCase
import com.example.appfilm.domain.usecase.firebase.realtime.CheckFavouriteMovieUseCase
import com.example.appfilm.domain.usecase.firebase.realtime.GetFavouriteMoviesUseCase
import com.example.appfilm.domain.usecase.firebase.realtime.RemoveFavouriteMovieUseCase
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
    val getMoviesUseCase: GetMoviesUseCase,

    val fetchDataAndSaveFromDbUseCase: FetchMovieAndSaveUseCase,
    val fetchCategoryUseCase: FetchCategoryUseCase,
    val fetchMoviesByCategoryUseCase: FetchMoviesByCategoryUseCase,
    val fetchDetailMovie : FetchDetailMovieUseCase,

    val addFavouriteMovieUseCase: AddFavouriteMovieUseCase,
    val checkFavouriteMovieUseCase: CheckFavouriteMovieUseCase,
    val getFavouriteMoviesUseCase: GetFavouriteMoviesUseCase,
    val removeFavouriteMovieUseCase: RemoveFavouriteMovieUseCase
) {


}