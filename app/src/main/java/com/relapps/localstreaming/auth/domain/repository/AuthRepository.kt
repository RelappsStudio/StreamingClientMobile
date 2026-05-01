package com.relapps.localstreaming.auth.domain.repository

interface AuthRepository {
    suspend fun login(username: String, password: String) : Result<String>
    suspend fun logout()
    fun getToken(): String?
}