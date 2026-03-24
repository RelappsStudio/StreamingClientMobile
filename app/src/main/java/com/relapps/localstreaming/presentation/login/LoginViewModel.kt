package com.relapps.localstreaming.presentation.login

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
            is LoginAction.PasswordChanged -> _state.update { it.copy(username = action.password) }
            is LoginAction.UsernameChanged -> _state.update { it.copy(username = action.username) }
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
                    _state.update { it.copy(isLoading = false, error = error.message) }
                }

        }
    }

}