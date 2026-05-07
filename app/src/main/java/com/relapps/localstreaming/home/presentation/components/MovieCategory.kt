package com.relapps.localstreaming.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.relapps.localstreaming.home.domain.Movie

@Composable
fun MovieCategory(
    title: String,
    movies: List<Movie>,
    onMovieClicked: () -> Unit,
    modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(title)
        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(movies.size) { index ->
                MovieCard(
                    modifier = Modifier.padding(16.dp),
                    onMovieClicked = onMovieClicked,
                    movie = movies[index])
            }
        }
    }
}