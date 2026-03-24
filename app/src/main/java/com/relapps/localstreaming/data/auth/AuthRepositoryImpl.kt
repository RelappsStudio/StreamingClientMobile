package com.relapps.localstreaming.data.auth

import com.relapps.localstreaming.data.TokenManager
import com.relapps.localstreaming.domain.auth.models.LoginRequest
import com.relapps.localstreaming.domain.auth.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
): AuthRepository {
    override suspend fun login(
        username: String,
        password: String
    ): Result<String> {
        return try {
            val response = api.login(LoginRequest(username, password))
            tokenManager.saveToken(response.token)
            Result.success(response.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        tokenManager.clearToken()

    }

    override fun getToken(): String? {
        TODO("Not yet implemented")
    }
}