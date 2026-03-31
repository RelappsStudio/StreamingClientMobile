package com.relapps.localstreaming.presentation.sharedComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.relapps.localstreaming.ui.theme.ObsidianLow
import com.relapps.localstreaming.ui.theme.ObsidianTheme
import com.relapps.localstreaming.ui.theme.PrimaryCoral

@Composable
fun ObsidianTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    leadingIcon: ImageVector,
    isPassword: Boolean = false,
    errorMessage: String? = null,
    modifier: Modifier = Modifier) {

    val dim = ObsidianTheme.dimensions
    var passwordVisible by remember { mutableStateOf(false) }

    Column(modifier) {
        Text(
            text = label.uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = if (errorMessage != null) MaterialTheme.colorScheme.error else Color.Gray,
            modifier = Modifier.padding(bottom = dim.paddingSmall)
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth(),
            textStyle = MaterialTheme.typography.bodyLarge,
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            cursorBrush = SolidColor(PrimaryCoral),
            decorationBox = {innerTextField ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dim.buttonHeight)
                        .background(ObsidianLow, RoundedCornerShape(dim.cardRadius))
                        .padding(horizontal = dim.paddingMedium),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = null,
                        tint = if(value.isNotEmpty()) Color.White else Color.Gray,
                        modifier = Modifier.size(20.dp) //TODO add icon sizes to core dimensions
                    )

                    Spacer(modifier = Modifier.width(dim.paddingMedium))

                    Box(modifier = Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(placeholder, style = MaterialTheme.typography.bodyLarge, color = Color.DarkGray)
                        }
                        innerTextField()
                    }

                    if(isPassword) {
                        IconButton(
                            onClick = {passwordVisible = !passwordVisible}
                        ) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password", //TODO internationalization and string to resource migration
                                tint = Color.Gray,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = dim.paddingSmall)
            )
        }
    }


}