package com.relapps.localstreaming.stories.domain

data class UserStoryGroup(
    val userId: String,
    val username: String,
    val userAvatarUrl: String,
    val pages: List<StoryPage>
)
