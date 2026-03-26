package com.relapps.localstreaming.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color


val ObsidianBase = Color(0xFF0E0E0E)
val ObsidianLow = Color(0xFF131313)
val ObsidianContainer = Color(0xFF1A1919)
val ObsidianHigh = Color(0xFF201F1F)
val ObsidianHighest = Color(0xFF262626)

val PrimaryCoral = Color(0xFFFF8E7D)
val PrimaryCoralDark = Color(0xFFFF7763)


val PrimaryGradient = Brush.linearGradient(
    colors = listOf(PrimaryCoral, PrimaryCoralDark),
    start = Offset(0f, 0f),
    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
)