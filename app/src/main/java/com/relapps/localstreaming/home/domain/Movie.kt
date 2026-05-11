package com.relapps.localstreaming.home.domain

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: String,
    val title: String,
    val imageUrl: String,
)
