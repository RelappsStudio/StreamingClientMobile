package com.relapps.localstreaming.domain.auth.models

data class LoginRequest(
    val username: String,
    val password: String,
)
