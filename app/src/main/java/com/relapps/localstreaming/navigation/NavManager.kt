package com.relapps.localstreaming.navigation

import androidx.navigation.NavHostController

object NavManager {
    private var navController: NavHostController? = null

    fun setController(controller: NavHostController?) {
        this.navController = controller
    }

    fun navigateTo(screen: Screen) {
        navController?.navigate(screen)
    }

    fun handleFlutterRequest(target: String){
        when(target) {
            "main" -> navigateTo(Screen.Main)
            "login" -> navigateTo(Screen.Login)
        }
    }
}