package com.snowtouch.auth_feature.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.auth_feature.presentation.login.LoginViewModel
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import org.koin.androidx.compose.koinViewModel

@Composable
fun Login(
    onCreateAccountClick: () -> Unit,
    onGoBackToLoginClick: () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState
    val loginResponseData by viewModel.loginResult.collectAsStateWithLifecycle()

    when (val loginResponse = loginResponseData) {
        is Result.Loading -> Loading()
        is Result.Success -> LoginContent(
            modifier = modifier,
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginButtonClick = { viewModel.login(uiState.email, uiState.password) },
            onCreateAccountClick = onCreateAccountClick
        )
        is Result.Failure -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = onGoBackToLoginClick,
            modifier = modifier,
            errorMessage = loginResponse.e.localizedMessage
        )
    }
}