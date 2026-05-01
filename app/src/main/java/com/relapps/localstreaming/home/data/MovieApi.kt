package com.relapps.localstreaming.home.data

import com.relapps.localstreaming.home.domain.Movie
import retrofit2.http.GET
//TODO: make it real connection when backend will be ready
interface MovieApi {
    @GET("api/content/movies")
    suspend fun getMovies() : List<Movie>
}