package com.relapps.localstreaming.home.presentation

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.home.data.PLACEHOLDER_IMAGE_URL
import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.presentation.components.MovieCard
@Preview
@Composable
fun homeScreenPreview() {
    HomeContent(
        state = HomeState(
            isLoading = false,
            movies = listOf(
                Movie("1", "Inception", PLACEHOLDER_IMAGE_URL),
                Movie("2", "Interstellar", PLACEHOLDER_IMAGE_URL),
                Movie("3", "The Dark Knight", PLACEHOLDER_IMAGE_URL)
            )
        )
    )
}
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
        val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    modifier: Modifier = Modifier) {

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("welcome") }
            )
        }
    ) {
        paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = if (state.isLoading) Arrangement.Center else Arrangement.SpaceEvenly
        ) {
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {

                    LazyRow(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(state.movies.size) { index ->
                            MovieCard(
                                modifier = Modifier.padding(16.dp),
                                movie = state.movies[index])
                        }
                    }
                    LazyRow {
                        items(state.movies.size) { index ->
                            MovieCard(
                                modifier = Modifier.padding(16.dp),
                                movie = state.movies[index])
                        }
                    }
                    LazyRow {
                        items(state.movies.size) { index ->
                            MovieCard(
                                modifier = Modifier.padding(16.dp),
                                movie = state.movies[index])
                        }
                    }
                LazyRow {
                    items(state.movies.size) { index ->
                        MovieCard(
                            modifier = Modifier.padding(16.dp),
                            movie = state.movies[index])
                    }
                }

            }
        }
    }
}