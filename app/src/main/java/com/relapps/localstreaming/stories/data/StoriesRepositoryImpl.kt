package com.relapps.localstreaming.stories.data

import com.relapps.localstreaming.stories.domain.StoriesRepository
import com.relapps.localstreaming.stories.domain.StoryPage
import com.relapps.localstreaming.stories.domain.UserStoryGroup
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject

val storyGroup = listOf(
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
class StoriesRepositoryImpl @Inject constructor(
    private val api: StoriesApiService
) : StoriesRepository {

    //TODO fix so that stories actually come from API
    private var cache: List<UserStoryGroup>? = null
    private val cacheMutex = Mutex()

    override suspend fun getLatestStories(): Result<List<UserStoryGroup>> {
        return cacheMutex.withLock {
            cache?.let {
                return Result.success(it)
            }
            runCatching {
                val response = api.getVerticalVideos()
                val domainStories = response.toDomain()
                cache = domainStories
                domainStories
            }
        }
    }
}