package com.relapps.localstreaming.ui.theme


import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color


object ObsidianTheme {
    val dimensions: AppDimensions
    @Composable
    get() = LocalAppDimensions.current

    val colors: ColorScheme
    @Composable
    get() = MaterialTheme.colorScheme
}

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryCoral,
    surface = ObsidianBase,
    onSurface = Color.White,
    background = ObsidianBase,
    onBackground = Color.White
)



@Composable
fun LocalStreamingTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalAppDimensions provides AppDimensions()) {
        MaterialTheme(
            colorScheme = DarkColorScheme,
            typography = ObsidianTypography,
            content = content
        )
    }
}