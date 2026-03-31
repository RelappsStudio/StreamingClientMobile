package com.relapps.localstreaming.presentation.login

data class LoginState(
    val isLoading: Boolean = false,
    val token: String? = null,
    val error: String? = null,
    val username: String = "",
    val password: String = "",
    val loginBypassTap: Int = 0
)
