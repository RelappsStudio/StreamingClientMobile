package com.relapps.localstreaming.home.data

import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.domain.MovieRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

const val PLACEHOLDER_IMAGE_URL = "https://placehold.co/600x400/orange/white"

class FakeMovieRepositoryImpl @Inject constructor(): MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        delay(500)
        return listOf(
            Movie("1", "Inception", PLACEHOLDER_IMAGE_URL),
            Movie("2", "Interstellar", PLACEHOLDER_IMAGE_URL),
            Movie("3", "The Dark Knight", PLACEHOLDER_IMAGE_URL)
        )
    }
}