package com.relapps.localstreaming.auth.data

import com.relapps.localstreaming.auth.domain.AuthStatus
import com.relapps.localstreaming.common.data.TokenManager
import com.relapps.localstreaming.auth.domain.models.LoginRequest
import com.relapps.localstreaming.auth.domain.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthApi,
    private val tokenManager: TokenManager
): AuthRepository {

    private val _authState = MutableStateFlow<AuthStatus>(AuthStatus.Loading)
    override val authState = _authState.asStateFlow()

    init {
        checkExistingSession()
    }

    private fun checkExistingSession() {
        val token = tokenManager.getToken()
        if (token != null) {
            //TODO: implement user data read from token or secondary call for user data
            _authState.value = AuthStatus.Authenticated(token, "Norman")
        } else {
            _authState.value = AuthStatus.Guest
        }
    }

    override suspend fun login(
        username: String,
        password: String
    ): Result<String> {
        return try {
            val response = api.login(LoginRequest(username, password))
            tokenManager.saveToken(response.token)
            _authState.value = AuthStatus.Authenticated(response.token, username)
            Result.success(response.token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun logout() {
        tokenManager.clearToken()
        _authState.value = AuthStatus.Guest

    }

    override fun getToken(): String? {
        TODO("Not yet implemented")
    }
}