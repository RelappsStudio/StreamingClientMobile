package com.relapps.localstreaming.home.data

import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.navigation.FlutterMovie

fun FlutterMovie.toDomain() = Movie(
    id = id,
    title = title,
    imageUrl = imageUrl
)