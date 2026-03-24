package com.relapps.localstreaming.presentation.login

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, name = "Initial state")
@Composable
fun PreviewLoginInitial() {
    LoginContent(state = LoginState(), onAction = {})
}

@Preview(showBackground = true, name = "Loading state")
@Composable
fun PreviewLoginLoading() {
    LoginContent(state = LoginState(isLoading = true), onAction = {})
}

@Preview(showBackground = true, name = "Error state")
@Composable
fun PreviewLoginError() {
    LoginContent(state = LoginState(error = "Cannot log in at this time"), onAction = {})
}