package com.snowtouch.auth_feature.presentation.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.auth_feature.presentation.login.components.Login
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.AdaptiveNavigationBar
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
fun LoginScreen(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    onNavMenuItemClick : (String) -> Unit,
    navigateToCreateAccount : () -> Unit,
    navigateToLoginScreen : () -> Unit,
) {

    SinglePageScaffold(
        bottomBar = {
            AdaptiveNavigationBar(
                currentScreen = currentScreen,
                displaySize = displaySize,
                onNavMenuItemClick = onNavMenuItemClick
            )
        }
    ) { innerPadding ->
        Login(
            onCreateAccountClick = navigateToCreateAccount,
            onGoBackToLoginClick = navigateToLoginScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}