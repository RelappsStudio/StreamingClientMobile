package com.relapps.localstreaming.presentation.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onBackNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LoginContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        content = { scaffoldPadding ->
            Column(
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .fillMaxSize()
                    .padding(16.dp)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TextField(
                    value = state.username,
                    onValueChange = {value ->
                        onAction(LoginAction.UsernameChanged(value))
                    },
                    label = {
                        Text("Username")
                    }
                )

                Button(
                    enabled = !state.isLoading,
                    onClick = {
                        keyboardController?.hide()
                        onAction(LoginAction.Login)
                    }
                ) {
                    Text(
                        if (state.isLoading) "Logging in..." else "Login"
                    )
                }
            }
        }
    )
}