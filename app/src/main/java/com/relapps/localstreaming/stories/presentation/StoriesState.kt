package com.relapps.localstreaming.stories.presentation

import com.relapps.localstreaming.stories.domain.UserStoryGroup

data class StoriesState(
    val isLoading: Boolean = false,
    val storyGroup: List<UserStoryGroup> =  emptyList(),
    val currentGroupIndex: Int = 0,
    val currentPageIndex: Int = 0,
    val error: String? = null
)
