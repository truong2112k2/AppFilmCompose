package com.example.appfilm.common

import com.example.appfilm.R

object Background {
    private val backgrounds = listOf(
        R.drawable.background_1,
        R.drawable.background_2,
        R.drawable.background_3,
        R.drawable.background_4,
        R.drawable.background_5,
    )

    val background = backgrounds.random()
}