package com.relapps.localstreaming.home.presentation.homeCore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.common.presentation.sharedComponents.ElevatedTopBar
import com.relapps.localstreaming.home.data.PLACEHOLDER_IMAGE_URL
import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.presentation.components.MovieCategory
import com.relapps.localstreaming.stories.domain.StoryPage
import com.relapps.localstreaming.stories.domain.UserStoryGroup
import com.relapps.localstreaming.stories.presentation.composables.StoriesRibbon
import com.relapps.localstreaming.stories.presentation.composables.StoriesRibbonWrapper

@Preview
@Composable
fun HomeScreenPreview() {
    HomeContent(
        state = HomeState(
            isLoading = false,
            movies = listOf(
                Movie("1", "Inception", PLACEHOLDER_IMAGE_URL),
                Movie("2", "Interstellar", PLACEHOLDER_IMAGE_URL),
                Movie("3", "The Dark Knight", PLACEHOLDER_IMAGE_URL)
            )
        ),
        onMovieClicked = {},
        onStoryClicked = {}
    )
}
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onMovieClicked: (Movie) -> Unit,
    onStoryClicked: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
        val state by viewModel.state.collectAsStateWithLifecycle()

    HomeContent(
        state = state,
        onMovieClicked = onMovieClicked,
        onStoryClicked = onStoryClicked
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    state: HomeState,
    onMovieClicked: (Movie) -> Unit,
    onStoryClicked: (Int) -> Unit,
    modifier: Modifier = Modifier) {

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
        Column(
            modifier = Modifier.padding(paddingValues),
        ) {
            StoriesRibbonWrapper(
                onStoryClicked = onStoryClicked
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = if (state.isLoading) Arrangement.Center else Arrangement.SpaceEvenly,

            ) {
                if (state.isLoading) {
                    item {
                        CircularProgressIndicator()
                    }

                } else {

                    //TODO: implement category by state propagation
//                items(state.categories) {category > MovieCategory()}

                    if (state.flutterMovies.isNotEmpty()) {
                        item {
                            MovieCategory(
                                title = "Special flutter bonus",
                                movies = state.flutterMovies,
                                onMovieClicked = onMovieClicked
                            )
                        }
                    }

                    item {
                        MovieCategory(
                            title = "Dramas",
                            movies = state.movies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        MovieCategory(
                            title = "Comedy",
                            movies = state.movies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        MovieCategory(
                            title = "Action",
                            movies = state.movies,
                            onMovieClicked = onMovieClicked
                        )
                    }
                    item {
                        MovieCategory(
                            title = "Horror",
                            movies = state.movies,
                            onMovieClicked = onMovieClicked
                        )
                    }








                }
            }
        }

    }
}