package com.relapps.localstreaming.stories.domain

data class StoryPage(
    val id: String,
    val videoUrl: String,
    val durationMs: Long = 5000L
)
