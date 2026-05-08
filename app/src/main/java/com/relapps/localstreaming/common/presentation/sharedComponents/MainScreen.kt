package com.relapps.localstreaming.common.presentation.sharedComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.relapps.localstreaming.home.presentation.homeCore.HomeScreen
import com.relapps.localstreaming.navigation.BottomNavItem
import com.relapps.localstreaming.navigation.Screen
import androidx.navigation.compose.composable
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    navController: NavController, //global nav controller for navigation to other screens if needed
    modifier: Modifier = Modifier) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val localNavController  = rememberNavController()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar() {
                val items = listOf(
                    BottomNavItem.Home,
                    BottomNavItem.Search,
                    BottomNavItem.Profile
                )

                items.forEach { item ->
                    val isSelected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class)} == true

                    NavigationBarItem(
                        selected = isSelected,
                        label = { Text(item.label) },
                        icon = { Icon(item.icon, contentDescription = null) },
                        onClick = {
                            localNavController.navigate(item.route) {
                                popUpTo(localNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                    )
                }


            }
        }
    ) { innerPadding ->
        NavHost(
            navController = localNavController,
            startDestination = Screen.Home,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable<Screen.Home> {
                HomeScreen()
            }
            composable<Screen.Search> {
//                TODO: make search feature
            }
            composable<Screen.Profile> {
//                TODO: make profile feature
            }
        }
    }
}