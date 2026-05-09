package com.relapps.localstreaming.home.domain

data class Actor(
    val id: String,
    val fullName: String,
    val imageUrl: String,
    val movies: List<Movie>
)
