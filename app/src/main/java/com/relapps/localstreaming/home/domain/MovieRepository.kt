package com.relapps.localstreaming.home.domain

interface MovieRepository {
    suspend fun getMovies() : List<Movie>
}