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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.common.presentation.sharedComponents.ElevatedTopBar
import com.relapps.localstreaming.home.data.PLACEHOLDER_IMAGE_URL
import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.presentation.components.MovieCard
import com.relapps.localstreaming.home.presentation.components.MovieCategory

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
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { ElevatedTopBar(
            title = "Welcome",
            scrollBehavior = scrollBehavior
        ) },
        bottomBar = {

        }
    ) {
        paddingValues ->
        LazyColumn(
            modifier = Modifier
//                .padding(paddingValues)
//                .verticalScroll(scrollState)
                .fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = if (state.isLoading) Arrangement.Center else Arrangement.SpaceEvenly,
            contentPadding = paddingValues
        ) {
            if (state.isLoading) {
                item {
                    CircularProgressIndicator()
                }

            } else {
                //TODO: implement category by state propagation
//                items(state.categories) {category > MovieCategory()}

                item {
                    MovieCategory(
                        title = "Dramas",
                        movies = state.movies,
                        onMovieClicked = {}
                    )
                }
                item {
                    MovieCategory(
                        title = "Comedy",
                        movies = state.movies,
                        onMovieClicked = {}
                    )
                }
                item {
                    MovieCategory(
                        title = "Action",
                        movies = state.movies,
                        onMovieClicked = {}
                    )
                }
                item {
                    MovieCategory(
                        title = "Horror",
                        movies = state.movies,
                        onMovieClicked = {}
                    )
                }








            }
        }
    }
}