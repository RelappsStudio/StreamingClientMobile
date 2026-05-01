package com.relapps.localstreaming.home.presentation

import com.relapps.localstreaming.home.domain.Movie

data class HomeState (
    val error : String = "",
    val isLoading: Boolean = true,
    val isGuest : Boolean = true,
    val movies : List<Movie> = listOf(),
)


