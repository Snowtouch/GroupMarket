package com.snowtouch.groupmarket.auth.presentation.login.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.auth.presentation.login.LoginViewModel
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.components.Loading
import com.snowtouch.groupmarket.core.presentation.components.theme.LoadingFailed
import org.koin.androidx.compose.koinViewModel

@Composable
fun Login(
    onCreateAccountClick: () -> Unit,
    onGoBackToLoginClick: () -> Unit,
    modifier : Modifier = Modifier,
    viewModel: LoginViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState
    val loginResponseData by viewModel.loginResponse.collectAsStateWithLifecycle()

    when (val loginResponse = loginResponseData) {
        is Response.Loading -> Loading()
        is Response.Success -> LoginContent(
            modifier = modifier,
            uiState = uiState,
            onEmailChange = viewModel::onEmailChange,
            onPasswordChange = viewModel::onPasswordChange,
            onLoginButtonClick = { viewModel.login(uiState.email, uiState.password) },
            onCreateAccountClick = onCreateAccountClick
        )
        is Response.Failure -> LoadingFailed(
            onRefreshClick = onGoBackToLoginClick,
            modifier = modifier,
            errorMessage = loginResponse.e.localizedMessage
        )
    }
}