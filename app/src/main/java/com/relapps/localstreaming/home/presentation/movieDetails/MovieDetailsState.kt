package com.relapps.localstreaming.home.presentation.movieDetails

import com.relapps.localstreaming.home.domain.MovieDetails

data class MovieDetailsState(
    val isLoading: Boolean = true,
    val currentMovieId: String = "empty", //for initial debugging only
    val details: MovieDetails? = null,
    val isError: Boolean = false,
    val error: String = "",
)
