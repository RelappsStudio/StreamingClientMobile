package com.relapps.localstreaming.auth.domain.usecases

import com.relapps.localstreaming.auth.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {

    suspend operator fun invoke(user: String, pass: String): Result<String> {
        //password or username validation lives here
        //this enforces invariant is maintained before trying to perform a malformed network call
        if (user.isEmpty() || pass.isEmpty()) {
            return Result.failure(IllegalArgumentException())
        }
        return repository.login(user, pass)
    }
}