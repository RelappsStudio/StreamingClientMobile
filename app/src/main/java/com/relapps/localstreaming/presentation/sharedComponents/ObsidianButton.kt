package com.relapps.localstreaming.presentation.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.relapps.localstreaming.ui.theme.PrimaryGradient


@Preview
@Composable
fun ObsidianButtonLoadingPreview() {
    ObsidianButton(
        text = "Example",
        onClick = {},
        isLoading = true,
    )
}
@Preview
@Composable
fun ObsidianButtonActivePreview() {
    ObsidianButton(
        text = "Example",
        onClick = {},
        isLoading = false,
    )
}

@Composable
fun ObsidianButton(
    text: String,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(PrimaryGradient)
            .clickable(enabled = !isLoading) {onClick()},
        contentAlignment = Alignment.Center
    )    {
        if (isLoading) {
            CircularProgressIndicator(
                color = Color.White,
                modifier = Modifier.size(24.dp)

            )
        } else {
            Text(text = text, style = MaterialTheme.typography.labelLarge)
        }
    }
}