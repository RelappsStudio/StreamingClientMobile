package com.relapps.localstreaming.home.presentation.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.SubcomposeAsyncImage
import com.relapps.localstreaming.home.domain.Movie

@Preview
@Composable
fun MovieCardPreview() {
    MovieCard(
        movie = Movie("1", "Example", "https://placehold.co/600x400/orange/white",),
        onMovieClicked = {}
    )
}

@Composable
fun MovieCard(
    movie: Movie,
    onMovieClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .size(width = 150.dp, height = 240.dp)
            .clickable(
                onClick = onMovieClicked
            )

    ) {

        SubcomposeAsyncImage(
            model = movie.imageUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentScale = ContentScale.Fit,
            loading = {
                CircularProgressIndicator()
            },
            error = {
                Icon(Icons.Default.Warning, contentDescription = "error")
            }
        )
        Text(
            text = movie.title,
            modifier = Modifier
                .padding(16.dp),
            textAlign = TextAlign.Center,
        )

    }
}