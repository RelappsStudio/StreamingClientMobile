package com.relapps.localstreaming.stories.domain

interface StoriesRepository {
    suspend fun getLatestStories(): Result<List<UserStoryGroup>>
}