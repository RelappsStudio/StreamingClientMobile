package com.relapps.localstreaming.stories.data

import com.relapps.localstreaming.stories.data.dto.PexelsResponseDTO
import com.relapps.localstreaming.stories.data.dto.PexelsVideoDTO
import com.relapps.localstreaming.stories.domain.StoryPage
import com.relapps.localstreaming.stories.domain.UserStoryGroup

fun PexelsResponseDTO.toDomain(): List<UserStoryGroup> {
    return this.videos.groupBy { it.user.id }
        .map { (userId, userVideos) ->
            val firstVideoUser = userVideos.first().user

            UserStoryGroup(
                userId = userId.toString(),
                username = firstVideoUser.name,
                userAvatarUrl = "https://ui-avatars.com/api/?name=${firstVideoUser.name}&background=random",
                pages = userVideos.map { it.toStoryPage() }
            )
        }
}

fun PexelsVideoDTO.toStoryPage(): StoryPage {
    val streamUrl = this.video_files
        .firstOrNull {it.file_type == "video/mp4"}?.link
        ?: this.video_files.first().link

   return  StoryPage(
        id = this.id.toString(),
        videoUrl = streamUrl,
        durationMs = this.duration * 1000L
    )
}