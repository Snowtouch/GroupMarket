package com.snowtouch.groupmarket.auth.presentation.login

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.auth.presentation.login.components.Login
import com.snowtouch.groupmarket.core.presentation.components.BottomNavigationBar
import com.snowtouch.groupmarket.core.presentation.components.NavigationRail
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

@Composable
fun LoginScreen(
    displaySize : DisplaySize,
    onBottomBarIconClick: (String) -> Unit,
    navigateToCreateAccount: () -> Unit,
    navigateToLoginScreen: () -> Unit
) {

    ScaffoldTemplate(
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(onNavItemClick = onBottomBarIconClick)
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