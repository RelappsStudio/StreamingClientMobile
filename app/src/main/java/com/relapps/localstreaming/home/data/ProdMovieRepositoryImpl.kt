package com.relapps.localstreaming.home.data

import com.relapps.localstreaming.common.data.TokenManager
import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.domain.MovieRepository
import javax.inject.Inject

class ProdMovieRepositoryImpl @Inject constructor(
    private val tokenManager: TokenManager
): MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        TODO("Not yet implemented")
    }

}