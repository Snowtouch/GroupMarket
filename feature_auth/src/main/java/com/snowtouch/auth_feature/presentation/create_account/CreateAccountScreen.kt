package com.snowtouch.auth_feature.presentation.create_account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.auth_feature.presentation.components.EmailField
import com.snowtouch.auth_feature.presentation.components.NameField
import com.snowtouch.auth_feature.presentation.components.PasswordField
import com.snowtouch.auth_feature.presentation.components.RepeatPasswordField
import com.snowtouch.core.presentation.components.CommonButton
import com.snowtouch.core.presentation.components.CommonTopAppBar
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.components.SinglePageScaffold

@Composable
internal fun CreateAccountScreen(
    viewModel : CreateAccountViewModel,
    onNavigateBack : () -> Unit,
    onAccountCreated : () -> Unit
) {

    val createAccountUiState by viewModel.uiState.collectAsStateWithLifecycle()

    SinglePageScaffold(
        topBar = {
            CommonTopAppBar(
                canNavigateBack = true,
                onNavigateBackClick = onNavigateBack
            )
        }
    ) {
        when(createAccountUiState.uiState) {

            is UiState.Error -> LoadingFailed(canRefresh = false)
            is UiState.Idle ->
                CreateAccountScreenContent(
                    uiState = createAccountUiState,
                    onEmailChange = viewModel::onEmailChange,
                    onNameChange = viewModel::onNameChange,
                    onPasswordChange = viewModel::onPasswordChange,
                    onRepeatPasswordChange = viewModel::onRepeatPasswordChange,
                    onSignUpClick = {
                        viewModel.createAccount(
                            createAccountUiState.email,
                            createAccountUiState.name,
                            createAccountUiState.password
                        )
                    }
                )
            is UiState.Loading -> Loading()
            is UiState.Success -> onAccountCreated()
        }
    }

}

@Composable
internal fun CreateAccountScreenContent(
    modifier : Modifier = Modifier,
    uiState : CreateAccountUiState,
    onEmailChange : (String) -> Unit,
    onNameChange : (String) -> Unit,
    onPasswordChange : (String) -> Unit,
    onRepeatPasswordChange : (String) -> Unit,
    onSignUpClick : () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "New account",
            style = MaterialTheme.typography.headlineLarge
        )
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
        uiState = CreateAccountUiState(UiState.Idle),
        onEmailChange = {},
        onPasswordChange = {},
        onRepeatPasswordChange = {},
        onSignUpClick = {},
        onNameChange = {}
    )
}