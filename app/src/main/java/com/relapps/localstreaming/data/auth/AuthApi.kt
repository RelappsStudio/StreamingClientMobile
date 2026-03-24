package com.relapps.localstreaming.data.auth

import com.relapps.localstreaming.domain.auth.models.LoginRequest
import com.relapps.localstreaming.domain.auth.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}