package com.snowtouch.groupmarket.screens.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.groupmarket.common.composable.CommonButton
import com.snowtouch.groupmarket.common.composable.EmailField
import com.snowtouch.groupmarket.common.composable.PasswordField
import org.koin.androidx.compose.koinViewModel

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel = koinViewModel(),
    onCreateAccountClick: () -> Unit
) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginButtonClick = { viewModel.login(uiState.email, uiState.password) },
        onCreateAccountClick = onCreateAccountClick
    )
}
@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    LoginBox(
        valueEmail = uiState.email,
        onNewValueEmail = onEmailChange,
        valuePassword = uiState.password,
        onNewValuePassword = onPasswordChange,
        onLoginButtonClick = onLoginButtonClick,
        onCreateAccountClick = onCreateAccountClick
    )
}
@Composable
fun LoginBox(
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard {
            Column(
                modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EmailField(value = valueEmail, onNewValue = onNewValueEmail)
                Spacer(modifier = modifier.height(8.dp))
                PasswordField(valuePassword, onNewValuePassword)
                CommonButton(onClick = onLoginButtonClick, text = "Login")
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
    LoginScreenContent(
        uiState = LoginUiState("123@gmail.com"),
        onEmailChange = {},
        onPasswordChange = {},
        onLoginButtonClick = {},
        onCreateAccountClick = {}
    )
}