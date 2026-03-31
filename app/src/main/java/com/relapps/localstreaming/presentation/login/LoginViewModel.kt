package com.relapps.localstreaming.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relapps.localstreaming.domain.auth.usecases.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(
) {

    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onAction(action: LoginAction) {
        when(action) {
            LoginAction.Login -> callLogin()
            LoginAction.Logout -> TODO() //why need a logout button on a login screen?
            LoginAction.BypassLogin -> loginBypass()
            is LoginAction.PasswordChanged -> _state.update { it.copy(password = action.password) }
            is LoginAction.UsernameChanged -> _state.update { it.copy(username = action.username) }

        }
    }

    private fun loginBypass() {
        val currentBypassTap = _state.value.loginBypassTap
        if (currentBypassTap < 2) {
            _state.update { it.copy(loginBypassTap = (currentBypassTap + 1)) }
        } else {
            _state.update { it.copy(username = "admin", password = "12345", loginBypassTap = 0) }
        }
    }

    private fun callLogin() {
        val currentState = _state.value
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            loginUseCase(currentState.username, currentState.password)
                .onSuccess { token ->
                    _state.update { it.copy(isLoading = false, token = token) }
                }
                .onFailure { error ->
                    Log.d("LOGIN", "Login failed $error")
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }

        }
    }

}