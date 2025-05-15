package com.example.appfilm.common

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.appfilm.R

object Constants {
    const val STATUS_TAG = "STATUS_TAG"
    const val ERROR_TAG = "ERROR_TAG"

    const val FIRST_ROUTE = "FIRST_SCREEN"

    const val LOG_IN_ROUTE = "LOG_IN_SCREEN"
    const val REGISTER_ROUTE = "REGISTER_SCREEN"
    const val HOME_ROUTE = "HOME_SCREEN"
    const val RESET_PASSWORD_ROUTE = "RESET_PASSWORD_ROUTE"
    const val CATEGORY_ROUTE = "CATEGORY_ROUTE"
    const val DETAIL_ROUTE = "DETAIL_ROUTE/{MovieSlug}"


    val FontTagessChrift = FontFamily(
        Font(R.font.tagesschrift)  // tên file không chứa đuôi .ttf
    )
    val FontNotoSans = FontFamily(
        Font(R.font.noto_sans)  // tên file không chứa đuôi .ttf
    )
}