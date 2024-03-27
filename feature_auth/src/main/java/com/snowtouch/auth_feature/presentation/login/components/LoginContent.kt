package com.snowtouch.auth_feature.presentation.login.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.auth_feature.presentation.components.EmailField
import com.snowtouch.auth_feature.presentation.components.PasswordField
import com.snowtouch.auth_feature.presentation.login.LoginUiState
import com.snowtouch.core.presentation.components.CommonButton

@Composable
fun LoginContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailField(
                    value = uiState.email,
                    onNewValue = onEmailChange
                )
                Spacer(modifier = Modifier.height(8.dp))
                PasswordField(
                    value = uiState.password,
                    onNewValue = onPasswordChange
                )
                CommonButton(
                    onClick = onLoginButtonClick,
                    text = "Login"
                )
            }
        }
        Text(
            text = "Don't have account ?",
            modifier = Modifier.padding(top = 24.dp),
            fontWeight = FontWeight.Medium
        )
        CommonButton(onClick = onCreateAccountClick, text = "Create account")
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    LoginContent(
        uiState = LoginUiState("123@gmail.com"),
        onEmailChange = {},
        onPasswordChange = {},
        onLoginButtonClick = {},
        onCreateAccountClick = {}
    )
}