package com.relapps.localstreaming.stories.data

import com.relapps.localstreaming.stories.data.dto.PexelsResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface StoriesApiService {

    @GET("videos/search")
    suspend fun getVerticalVideos(
        @Query("query") query: String = "vertical nature",
        @Query("per_page") perPage: Int = 15
    ) : PexelsResponseDTO
}