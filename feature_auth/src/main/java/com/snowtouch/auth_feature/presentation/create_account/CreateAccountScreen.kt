package com.snowtouch.auth_feature.presentation.create_account

import androidx.compose.foundation.layout.Box
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
import com.snowtouch.auth_feature.presentation.components.EmailField
import com.snowtouch.auth_feature.presentation.components.NameField
import com.snowtouch.auth_feature.presentation.components.PasswordField
import com.snowtouch.auth_feature.presentation.components.RepeatPasswordField
import com.snowtouch.core.presentation.components.CommonButton

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountViewModel
) {

    val uiState by viewModel.uiState

    CreateAccountScreenContent(
        uiState = uiState,
        onEmailChange = viewModel::onEmailChange,
        onNameChange = viewModel::onNameChange,
        onPasswordChange = viewModel::onPasswordChange,
        onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
        onSignUpClick = { viewModel.createAccount(uiState.email, uiState.name, uiState.password) }
    )

}
@Composable
fun CreateAccountScreenContent(
    modifier: Modifier = Modifier,
    uiState: CreateAccountUiState,
    onEmailChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onRepeatPasswordChange: (String) -> Unit,
    onSignUpClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ) {
        ElevatedCard(modifier = modifier.padding(16.dp)) {
            Column(
                modifier = modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                EmailField(uiState.email, onEmailChange)
                Spacer(modifier = modifier.height(8.dp))

                NameField(uiState.name, onNameChange)
                Spacer(modifier = modifier.height(8.dp))

                PasswordField(uiState.password, onPasswordChange)
                Spacer(modifier = modifier.height(8.dp))

                RepeatPasswordField(uiState.repeatPassword, onRepeatPasswordChange)

                CommonButton(
                    onClick = onSignUpClick,
                    text = "Sign up"
                )
            }
        }
    }
}
@Preview(showSystemUi = true)
@Composable
fun CreateAccountScreenPreview() {
    CreateAccountScreenContent(
        uiState = CreateAccountUiState("123@gmail.com"),
        onEmailChange = {},
        onPasswordChange = {},
        onRepeatPasswordChange = {},
        onSignUpClick = {},
        onNameChange = {}
    )
}