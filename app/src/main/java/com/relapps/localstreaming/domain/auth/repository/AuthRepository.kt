package com.relapps.localstreaming.domain.auth.repository

interface AuthRepository {
    suspend fun login(username: String, password: String) : Result<String>
    suspend fun logout()
    fun getToken(): String?
}