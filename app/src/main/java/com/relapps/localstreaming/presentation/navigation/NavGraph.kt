package com.relapps.localstreaming.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.relapps.localstreaming.presentation.login.LoginScreen
import com.relapps.localstreaming.presentation.onboarding.OnboardingScreen
import kotlinx.serialization.Serializable

sealed interface Screen {
    @Serializable
    data object Onboarding: Screen
    @Serializable
    data object Login: Screen
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding,
    ) {


        composable<Screen.Onboarding> {
            OnboardingScreen(onNavigateToLogin = { navController.navigate(Screen.Login) })
        }

        composable<Screen.Login> {
            LoginScreen(onBackNavigate = {navController.popBackStack()})
        }
    }
}