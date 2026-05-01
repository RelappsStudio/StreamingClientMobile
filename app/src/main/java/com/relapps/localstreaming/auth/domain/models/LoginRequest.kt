package com.relapps.localstreaming.auth.domain.models

data class LoginRequest(
    val username: String,
    val password: String,
)
