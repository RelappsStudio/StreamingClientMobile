package com.relapps.localstreaming.presentation.login

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.relapps.localstreaming.R
import com.relapps.localstreaming.presentation.sharedComponents.ObsidianButton
import com.relapps.localstreaming.presentation.sharedComponents.ObsidianTextField
import com.relapps.localstreaming.ui.theme.ObsidianBase
import com.relapps.localstreaming.ui.theme.ObsidianContainer
import com.relapps.localstreaming.ui.theme.ObsidianTheme
import com.relapps.localstreaming.ui.theme.PrimaryCoral

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    onBackNavigate: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    //TODO figure out if launched effect navigation is better than passing top argument from nav graph
    LaunchedEffect(state.token) {
        if (state.token != null) {
            // Navigate to Home screen
        }
    }

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
    val dim = ObsidianTheme.dimensions
    val localFocusManager = LocalFocusManager.current

    Scaffold(
        containerColor = ObsidianBase
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = paddingValues.calculateTopPadding(),
                    bottom = paddingValues.calculateBottomPadding(),
                    start = dim.screenHorizontal / 2,
                    end = dim.screenHorizontal / 2
                )
        ) {

            Text(
                modifier = Modifier.clickable(onClick = {onAction(LoginAction.BypassLogin)}),
                text = "Local Streaming\nSpotlight",
                style = MaterialTheme.typography.headlineLarge,
                color = PrimaryCoral, // The defining accent
                lineHeight = 44.sp //TODO make into theme dimension
            )

            Spacer(modifier = Modifier.height(dim.paddingExtraLarge))


            Column(modifier = Modifier.weight(1f)) {

                ObsidianTextField(
                    value = state.username,
                    onValueChange = { onAction(LoginAction.UsernameChanged(it)) },
                    label = "Username",
                    placeholder = "Example: CoolBeanson420",
                    leadingIcon = Icons.Default.Person,
                    errorMessage = state.error?.takeIf { it.contains("Email", ignoreCase = true) }
                )

                Spacer(modifier = Modifier.height(dim.paddingMedium))

                ObsidianTextField(
                    value = state.password,
                    onValueChange = { onAction(LoginAction.PasswordChanged(it)) },
                    label = "Password",
                    placeholder = "••••••••",
                    leadingIcon = Icons.Default.Lock,
                    isPassword = true,
                    errorMessage = state.error?.takeIf { it.contains("Password", ignoreCase = true) }
                )

                TextButton(
                    onClick = {}, //TODO Forgot Password implementation once BE gets smart enough
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(top = dim.paddingSmall)
                ) {
                    Text("Forgot Password?", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }

                Spacer(modifier = Modifier.height(dim.paddingLarge))

                // The Gradient Coral Button (Fixed: clickable = !isLoading)
                ObsidianButton(
                    text = "Log In",
                    isLoading = state.isLoading,

                    onClick = {
                        keyboardController?.hide()
                        localFocusManager.clearFocus()
                        onAction(LoginAction.Login)
                    }
                )

                Spacer(modifier = Modifier.height(dim.paddingLarge))

                // FIXME: button renders differently on preview and in app. To investigate and fix for google integration
                OutlinedButton(
                    onClick = { }, //TODO login with google if I ever get to integrate firebase with analytics, crashlytics, remote config and performance
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dim.buttonHeight),
                    shape = RoundedCornerShape(dim.cardRadius),
                    border = BorderStroke(1.dp, ObsidianContainer),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Icon(painterResource(R.drawable.smoke_placeholder), contentDescription = null, modifier = Modifier.size(18.dp)) //TODO replace placeholder for google icon
                    Spacer(modifier = Modifier.width(dim.paddingSmall))
                    Text("Login with Google")
                }
            }
            //TODO adjust bottom padding or spacer/weight here
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = dim.paddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ObsidianButton(
                    text = "Continue as Guest",
                    onClick = {  } //TODO implement continue as guest to demo only suggestion engine
                )

                Spacer(modifier = Modifier.height(dim.paddingMedium))

                //TODO this button is entirely unnecessary but looks good on screen
                Row {
                    Text("Don't have an account?", color = Color.Gray)
                    Spacer(modifier = Modifier.width(dim.paddingSmall))
                    Text(
                        text = "Sign Up",
                        color = PrimaryCoral,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.clickable {}
                    )
                }
            }
        }
    }


}