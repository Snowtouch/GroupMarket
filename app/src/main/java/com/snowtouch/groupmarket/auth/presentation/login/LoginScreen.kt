package com.snowtouch.groupmarket.auth.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.auth.presentation.components.EmailField
import com.snowtouch.groupmarket.auth.presentation.components.PasswordField
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.components.CommonButton
import com.snowtouch.groupmarket.core.presentation.components.Loading
import com.snowtouch.groupmarket.core.presentation.util.SnackbarState

@Composable
fun LoginScreen(
    viewModel: LoginScreenViewModel,
    onCreateAccountClick: () -> Unit,
    onLoginButtonClick: () -> Unit
) {
    val uiState by viewModel.uiState

    val loginResponse by viewModel.loginResponse.collectAsStateWithLifecycle()

    Scaffold(
        content = {
            when (loginResponse) {
                is Response.Loading -> Loading()
                is Response.Success -> LoginScreenContent(
                    modifier = Modifier.padding(paddingValues = it),
                    uiState = uiState,
                    onEmailChange = viewModel::onEmailChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onLoginButtonClick = {
                        viewModel.login(uiState.email, uiState.password)
                        onLoginButtonClick() },
                    onCreateAccountClick = onCreateAccountClick)
                is Response.Failure -> loginResponse.apply {
                    viewModel.showSnackbar(SnackbarState.ERROR, this.toString())
                }
            }


        }
    )
}
@Composable
fun LoginScreenContent(
    modifier: Modifier = Modifier,
    uiState: LoginUiState,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onCreateAccountClick: () -> Unit
) {
    LoginBox(
        modifier = modifier,
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
    modifier: Modifier = Modifier,
    valueEmail: String,
    onNewValueEmail: (String) -> Unit,
    valuePassword: String,
    onNewValuePassword: (String) -> Unit,
    onLoginButtonClick: () -> Unit,
    onCreateAccountClick: () -> Unit,
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