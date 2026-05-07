package com.relapps.localstreaming.home.data

import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.domain.MovieRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

const val PLACEHOLDER_IMAGE_URL = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_color_272x92dp.png"

class FakeMovieRepositoryImpl @Inject constructor(): MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        delay(500)
        return listOf(
            Movie("1", "Inception", PLACEHOLDER_IMAGE_URL),
            Movie("2", "Interstellar", PLACEHOLDER_IMAGE_URL),
            Movie("3", "The Dark Knight", PLACEHOLDER_IMAGE_URL),
            Movie("4", "Shawshank Redemption", PLACEHOLDER_IMAGE_URL),
            Movie("5", "Dark Knight Rises", PLACEHOLDER_IMAGE_URL),
            Movie("6", "Star Wars: Revenge of the Sith", PLACEHOLDER_IMAGE_URL)
        )
    }

    override suspend fun getPromos(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getContinueWatching(): List<Movie> {
        TODO("Not yet implemented")
    }

    override suspend fun getCategoryHighlights(category: String): List<Movie> {
        TODO("Not yet implemented")
    }
}