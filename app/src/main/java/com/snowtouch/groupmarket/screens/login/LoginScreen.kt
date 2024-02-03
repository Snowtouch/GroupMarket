package com.snowtouch.groupmarket.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.groupmarket.common.composable.CommonButton
import com.snowtouch.groupmarket.common.composable.EmailField
import com.snowtouch.groupmarket.common.composable.PasswordField

@Composable
fun LoginScreen(viewModel: LoginScreenViewModel) {
    val uiState by viewModel.uiState

    LoginScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginButtonClick = { viewModel.login(uiState.email, uiState.password) }
    )
}
@Composable
fun LoginScreenContent(
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit
) {
    LoginBox(
        valueEmail = uiState.email,
        onNewValueEmail = onEmailChange,
        valuePassword = uiState.password,
        onNewValuePassword = onPasswordChange,
        onLoginButtonClick = onLoginButtonClick
    )
}
@Composable
fun LoginBox(
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp, bottom = 8.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard {
            Column(modifier.padding(24.dp)) {
                EmailField(value = valueEmail, onNewValue = onNewValueEmail)
                Spacer(modifier = modifier.height(8.dp))
                PasswordField(valuePassword, onNewValuePassword)
                CommonButton(onClick = onLoginButtonClick, text = "Login")
            }
        }
    }
}
@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreenContent(
        uiState = LoginUiState("123@gmail.com"),
        onEmailChange = {},
        onPasswordChange = {},
        onLoginButtonClick = {}
    )
}