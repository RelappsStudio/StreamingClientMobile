package com.relapps.localstreaming.home.domain

data class MovieDetails(
    val movieID: String,
    val title: String,
    val imageUrl: String,
    val releaseDate: String,
    val actors: List<Actor>,
    val description: String,
)
