package com.snowtouch.groupmarket.screens.create_account

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.snowtouch.groupmarket.common.composable.NameField
import com.snowtouch.groupmarket.common.composable.PasswordField
import com.snowtouch.groupmarket.common.composable.RepeatPasswordField
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateAccountScreen(
    viewModel: CreateAccountScreenViewModel = koinViewModel()
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
    ElevatedCard(modifier = modifier.padding(16.dp)) {
        Column(
            modifier = Modifier,
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