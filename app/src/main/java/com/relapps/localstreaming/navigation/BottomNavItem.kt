package com.relapps.localstreaming.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

sealed class BottomNavItem(
    val route: Screen,
    val label: String,
    val icon: ImageVector
) {
    data object Home: BottomNavItem(Screen.Home, "Home", Icons.Default.Home)
    data object Profile: BottomNavItem(Screen.Profile, "Profile", Icons.Default.Person)
    data object Search: BottomNavItem(Screen.Search, "Search", Icons.Default.Search)
}
