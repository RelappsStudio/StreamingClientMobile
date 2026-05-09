package com.relapps.localstreaming.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import com.relapps.localstreaming.auth.presentation.login.LoginScreen
import com.relapps.localstreaming.auth.presentation.onboarding.OnboardingScreen
import com.relapps.localstreaming.common.presentation.sharedComponents.MainScreen
import com.relapps.localstreaming.home.domain.Movie
import com.relapps.localstreaming.home.presentation.homeCore.HomeScreen
import com.relapps.localstreaming.home.presentation.movieDetails.MovieDetailsScreen
import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    data class MovieDetails(val movieId: String): Screen
    @Serializable
    object Search: Screen

    @Serializable
    object Profile: Screen
    @Serializable
    data object Home: Screen

  @Serializable
  object Main: Screen
    @Serializable
    data object Onboarding: Screen
    @Serializable
    data object Login: Screen
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    DisposableEffect(navController) {
        NavManager.setController(navController)
        onDispose { NavManager.setController(null) }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Onboarding,
    ) {

        composable<Screen.MovieDetails> {
            MovieDetailsScreen()
        }

        composable<Screen.Home> {
            HomeScreen(
                onMovieClicked = { movie -> navController.navigate(Screen.MovieDetails(movieId = movie.id))}
            )
        }

        composable<Screen.Main> {
            MainScreen(navController)
        }


        composable<Screen.Onboarding> {
            OnboardingScreen(onNavigateToLogin = { navController.navigate(Screen.Login) })
        }

        composable<Screen.Login> {
            LoginScreen(
                onBackNavigate = {navController.popBackStack()} ,
                onLoginSuccess = {
                    navController.navigate(Screen.Main) {
                        popUpTo(Screen.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}