package com.relapps.localstreaming.auth.domain

sealed interface AuthStatus {
    object Loading : AuthStatus
    object Guest : AuthStatus
    data class Authenticated(val token: String, val username: String): AuthStatus
}