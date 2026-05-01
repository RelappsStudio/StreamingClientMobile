package com.relapps.localstreaming.auth.data

import com.relapps.localstreaming.auth.domain.models.LoginRequest
import com.relapps.localstreaming.auth.domain.models.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("api/auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse
}