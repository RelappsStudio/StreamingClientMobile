package com.relapps.localstreaming.stories.data.dto

data class PexelsVideoDTO(
    val id: Long,
    val width: Int,
    val height: Int,
    val duration: Int,
    val user: PexelsUserDTO,
    val video_files: List<PexelsFileDTO>
)
