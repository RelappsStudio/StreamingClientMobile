package com.relapps.localstreaming

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.relapps.localstreaming.navigation.AppNavigation
import com.relapps.localstreaming.ui.theme.LocalStreamingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LocalStreamingTheme {
                //TODO: verification if user is already logged in based on existing token and call to validate
                AppNavigation()
            }
        }
    }
}
