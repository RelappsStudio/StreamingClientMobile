package com.relapps.localstreaming.auth.domain.repository

import com.relapps.localstreaming.auth.domain.AuthStatus
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    val authState: StateFlow<AuthStatus>
    suspend fun login(username: String, password: String) : Result<String>
    suspend fun logout()
    fun getToken(): String?
}