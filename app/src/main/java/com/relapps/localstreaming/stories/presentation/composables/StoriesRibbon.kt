package com.relapps.localstreaming.stories.presentation.composables


import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.relapps.localstreaming.stories.domain.UserStoryGroup

@Composable
fun StoriesRibbon(
    storyGroups: List<UserStoryGroup>,
    onUserStoryClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    ) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        itemsIndexed(storyGroups) {index, group ->
            StoryBubbleItem(group= group, onClick = {onUserStoryClick(index)})
        }
    }
}

@Composable
fun StoryBubbleItem(group: UserStoryGroup, onClick: () -> Unit) {
    val ringGradient = linearGradient(
        colors = listOf(Color(0xFF833AB4), Color(0xFFF56040), Color(0xFFFCAF45))
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable {onClick()}.width(72.dp)
    ) {
        //TODO remove gradient colors from viewed stories
        Box(
            modifier = Modifier.size(68.dp).border(2.dp, ringGradient, CircleShape)
        ) {
            AsyncImage(
                model = group.userAvatarUrl,
                contentDescription = "${group.username}'s story",
                modifier = Modifier.fillMaxSize().clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = group.username,
            fontSize = 11.sp,
            maxLines = 1,
        )
    }
}