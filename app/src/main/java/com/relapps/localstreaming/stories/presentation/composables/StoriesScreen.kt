package com.relapps.localstreaming.stories.presentation.composables

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import com.relapps.localstreaming.common.presentation.player.VideoPlayer
import com.relapps.localstreaming.stories.domain.StoryPage
import com.relapps.localstreaming.stories.domain.UserStoryGroup
import com.relapps.localstreaming.stories.presentation.StoriesAction
import com.relapps.localstreaming.stories.presentation.StoriesEffect
import com.relapps.localstreaming.stories.presentation.StoriesState
import com.relapps.localstreaming.stories.presentation.StoriesViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun StoriesScreen(
    onNavigateBack: () -> Unit,
    viewModel: StoriesViewModel = hiltViewModel(),
    modifier: Modifier = Modifier) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when(effect) {
                is StoriesEffect.ExitStories -> onNavigateBack()
            }
        }
    }

        StoriesContent(
            state = state,
            onAction = viewModel::onAction,
            modifier = modifier,
        )





}

@Composable
fun StoriesContent(
    state: StoriesState,
    onAction: (StoriesAction) -> Unit,
    modifier: Modifier = Modifier) {

    if (state.storyGroup.isEmpty()) return

    val pagerState = rememberPagerState(initialPage = state.currentGroupIndex) {
        state.storyGroup.size
    }

    LaunchedEffect(pagerState.currentPage) {
        if (pagerState.currentPage != state.currentGroupIndex) {
            onAction(StoriesAction.GroupSwiped(pagerState.currentPage))
        }
    }

    LaunchedEffect(state.currentGroupIndex) {
        if (pagerState.currentPage != state.currentGroupIndex) {
            pagerState.animateScrollToPage(state.currentGroupIndex)
        }
    }

    VerticalPager(
        state = pagerState,
        modifier = modifier.fillMaxSize().background(Color.Black)
    ) { index ->
        val currentGroup = state.storyGroup[index]
        val isTargetGroup = index == state.currentGroupIndex
        var storyProgress by remember { mutableFloatStateOf(0f) }

        LaunchedEffect(state.currentPageIndex, state.currentGroupIndex) {
            storyProgress = 0f
        }


            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize()

            ) {
                val boxScope = this
                val activePage = currentGroup.pages.getOrNull(state.currentPageIndex)

                if (activePage != null) {
                    VideoPlayer(
                        videoUrl = activePage.videoUrl,
                        isPlaying = isTargetGroup,
                        onVideoEnded = {
                            onAction(StoriesAction.NextPage)
                        },
                        onProgressChanged = {progress ->
                            storyProgress = progress
                        },
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .pointerInput(state.currentPageIndex, state.currentGroupIndex) {
                        detectTapGestures { offset ->
                            val screenWidth = boxScope.maxWidth.toPx()

                            //if user taps on the left 30 % of the screen go to previous page
                            //else go to next page
                            if (offset.x < screenWidth * 0.3f) {
                                onAction(StoriesAction.PreviousPage)
                            } else {
                                onAction(StoriesAction.NextPage)
                            }
                        }
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color.Black.copy(alpha = 0.5f), Color.Transparent)
                                )
                            )
                    )

                    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 50.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            currentGroup.pages.forEachIndexed { index, page ->
                                val progress = when {
                                    index < state.currentPageIndex -> 1f
                                    index == state.currentPageIndex && isTargetGroup -> storyProgress
                                    else -> 0f
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(3.dp)
                                        .clip(CircleShape)
                                        .background(Color.White.copy(alpha = 0.4f))
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .fillMaxWidth(progress)
                                            .background(Color.White)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            AsyncImage(
                                model = currentGroup.userAvatarUrl,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp).clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = currentGroup.username, color = Color.White)
                        }
                    }

                }
            }

    }



}

@Preview(showBackground = true)
@Composable
fun StoriesContentPreview() {
    val mockState = StoriesState(
        currentGroupIndex = 0,
        currentPageIndex = 1,
        storyGroup = listOf(
            UserStoryGroup(
                userId = "1",
                username = "Alice Johnson",
                userAvatarUrl = "https://ui-avatars.com/api/?name=Alice",
                pages = listOf(
                    StoryPage("p1", "url1"),
                    StoryPage("p2", "url2"),
                    StoryPage("p3", "url3")
                )
            ),
            UserStoryGroup(
                userId = "2",
                username = "Bob Smith",
                userAvatarUrl = "https://ui-avatars.com/api/?name=Bob",
                pages = listOf(
                    StoryPage("p4", "url4")
                )
            )
        )
    )

    StoriesContent(
        state = mockState,
        onAction = {}
    )
}