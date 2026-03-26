package com.relapps.localstreaming.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class AppDimensions(
    val paddingSmall: Dp = 8.dp,
    val paddingMedium: Dp = 16.dp,
    val paddingLarge: Dp = 24.dp,
    val paddingExtraLarge: Dp = 32.dp,
    val screenHorizontal: Dp = 24.dp,
    val buttonHeight: Dp = 56.dp,
    val cardRadius: Dp = 16.dp,
    val glassBlur: Dp = 24.dp
)

val LocalAppDimensions = staticCompositionLocalOf { AppDimensions() }