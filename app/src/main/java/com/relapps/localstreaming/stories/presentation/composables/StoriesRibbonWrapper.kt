package com.relapps.localstreaming.stories.presentation.composables

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.stories.presentation.StoriesRibbonViewModel

@Composable
fun StoriesRibbonWrapper(
    onStoryClicked: (Int) -> Unit,
    viewModel: StoriesRibbonViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    if (state.storyGroups.isNotEmpty()) {
        StoriesRibbon(
            storyGroups = state.storyGroups,
            onUserStoryClick = onStoryClicked,
            modifier = modifier
        )
    }

}