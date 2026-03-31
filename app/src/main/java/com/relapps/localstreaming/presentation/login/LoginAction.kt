package com.relapps.localstreaming.presentation.login

sealed interface LoginAction {
    data class UsernameChanged(val username: String) : LoginAction
    data class PasswordChanged(val password: String) : LoginAction
    object Login : LoginAction
    object Logout : LoginAction
    object BypassLogin: LoginAction
}

