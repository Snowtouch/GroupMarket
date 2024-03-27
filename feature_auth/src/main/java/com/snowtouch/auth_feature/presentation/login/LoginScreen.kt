package com.snowtouch.auth_feature.presentation.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.auth_feature.presentation.login.components.Login
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.BottomNavigationBar
import com.snowtouch.core.presentation.components.NavigationRail
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
fun LoginScreen(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    onBottomBarIconClick : (String) -> Unit,
    navigateToCreateAccount : () -> Unit,
    navigateToLoginScreen : () -> Unit
) {

    ScaffoldTemplate(
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavItemClick = onBottomBarIconClick
                )
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onBottomBarIconClick)
            }
        }
    ) { innerPadding ->
        Login(
            onCreateAccountClick = navigateToCreateAccount,
            onGoBackToLoginClick = navigateToLoginScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}