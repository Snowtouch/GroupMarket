package com.snowtouch.auth_feature.presentation.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.auth_feature.presentation.login.components.Login
import com.snowtouch.core.presentation.components.SinglePageScaffold

@Composable
fun LoginScreen(
    navigateToCreateAccount : () -> Unit,
    navigateToLoginScreen : () -> Unit,
) {

    SinglePageScaffold(
    ) { innerPadding ->
        Login(
            onCreateAccountClick = navigateToCreateAccount,
            onGoBackToLoginClick = navigateToLoginScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}