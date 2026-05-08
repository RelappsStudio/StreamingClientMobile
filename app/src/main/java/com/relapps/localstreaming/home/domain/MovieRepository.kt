package com.relapps.localstreaming.home.domain

interface MovieRepository {
    suspend fun getMovies() : List<Movie>
    suspend fun getPromos(): List<Movie>
    suspend fun getContinueWatching(): List<Movie>
    suspend fun getCategoryHighlights(category: String): List<Movie>
//    suspend fun getMovieDetails(): TODO make movie details screen, model and fetching
}