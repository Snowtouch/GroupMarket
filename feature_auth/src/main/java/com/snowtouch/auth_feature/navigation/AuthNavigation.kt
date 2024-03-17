package com.snowtouch.auth_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.auth_feature.presentation.create_account.CreateAccountScreen
import com.snowtouch.auth_feature.presentation.create_account.CreateAccountViewModel
import com.snowtouch.auth_feature.presentation.login.LoginScreen
import com.snowtouch.core.presentation.util.DisplaySize
import org.koin.androidx.compose.koinViewModel

sealed class AuthRoutes(val route : String) {
    data object Login : AuthRoutes("login")
    data object CreateAccount : AuthRoutes("create_account")
}

fun NavGraphBuilder.authFeature(
    displaySize : DisplaySize,
    navController : NavHostController,
) {
    navigation(startDestination = AuthRoutes.Login.route, route = "auth_feature") {
        composable(route = AuthRoutes.Login.route) {

            LoginScreen(
                displaySize = displaySize,
                onBottomBarIconClick = { route -> navController.navigate(route) },
                navigateToCreateAccount = { navController.navigateToCreateAccount() },
                navigateToLoginScreen = { navController.navigateToLogin() }
            )
        }

        composable(route = AuthRoutes.CreateAccount.route) {

            val viewModel : CreateAccountViewModel = koinViewModel()

            CreateAccountScreen(
                viewModel = viewModel
            )
        }
    }
}

fun NavController.navigateToLogin() {
    this.navigate(route = AuthRoutes.Login.route)
}

fun NavController.navigateToCreateAccount() {
    this.navigate(route = AuthRoutes.CreateAccount.route)
}