package com.snowtouch.auth_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.auth_feature.presentation.create_account.CreateAccountScreen
import com.snowtouch.auth_feature.presentation.create_account.CreateAccountViewModel
import com.snowtouch.auth_feature.presentation.login.LoginScreen
import org.koin.androidx.compose.koinViewModel

sealed class AuthRoutes(val route : String) {
    data object AuthFeature : AuthRoutes("auth_feature")
    data object Login : AuthRoutes("login")
    data object CreateAccount : AuthRoutes("create_account")
}

fun NavGraphBuilder.authFeature(
    navController : NavHostController,
    navigateToHome : () -> Unit,
) {
    navigation(startDestination = AuthRoutes.Login.route, route = AuthRoutes.AuthFeature.route) {
        composable(route = AuthRoutes.Login.route) {

            LoginScreen(
                navigateToCreateAccount = { navController.navigateToCreateAccount() },
                navigateToLoginScreen = { navController.navigateToLogin() }
            )
        }

        composable(route = AuthRoutes.CreateAccount.route) {

            val viewModel : CreateAccountViewModel = koinViewModel()

            CreateAccountScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAccountCreated = navigateToHome
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