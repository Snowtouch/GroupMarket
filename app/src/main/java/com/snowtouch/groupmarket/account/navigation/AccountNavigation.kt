package com.snowtouch.groupmarket.account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.account.presentation.AccountScreen
import com.snowtouch.groupmarket.account.presentation.AccountScreenViewModel
import com.snowtouch.groupmarket.auth.navigation.AuthRoutes
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreen
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.login.LoginScreen
import com.snowtouch.groupmarket.auth.presentation.login.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel

sealed class AccountRoutes(val route: String) {
    data object Account: AccountRoutes("account")
}

fun NavGraphBuilder.accountFeature(
    navController: NavController,
    isLoggedIn: Boolean
) {
    val startDestination =
        when (isLoggedIn) {
            true -> AccountRoutes.Account.route
            false -> AuthRoutes.Login.route
        }

    navigation(startDestination = startDestination, route = "auth") {

        composable(AccountRoutes.Account.route) {
            val viewModel: AccountScreenViewModel = koinViewModel()

            AccountScreen(
                viewModel = viewModel,
                onNavigateToOptionClick = { navController.navigate(it) },
                onSignOutNavigate = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Login.route) { inclusive = true}
                    }
                }
            )
        }
    }
}

fun NavController.navigateToAccount() {
    this.navigate(route = AccountRoutes.Account.route)
}