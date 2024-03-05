package com.snowtouch.groupmarket.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreen
import com.snowtouch.groupmarket.auth.presentation.create_account.CreateAccountScreenViewModel
import com.snowtouch.groupmarket.auth.presentation.login.LoginScreen
import com.snowtouch.groupmarket.auth.presentation.login.LoginScreenViewModel
import com.snowtouch.groupmarket.home.navigation.HomeRoute
import org.koin.androidx.compose.koinViewModel

sealed class AuthRoutes(val route: String) {
    data object Login: AuthRoutes("login")
    data object CreateAccount: AuthRoutes("create_account")
}

fun NavGraphBuilder.authFeature(
    navController: NavHostController
) {
    navigation(startDestination = AuthRoutes.Login.route, route = "auth") {
        composable(route = AuthRoutes.Login.route) {

            val viewModel: LoginScreenViewModel = koinViewModel()

            LoginScreen(
                viewModel = viewModel,
                onCreateAccountClick = { navController.navigate(AuthRoutes.CreateAccount.route) },
                onLoginButtonClick = { navController.navigate(HomeRoute.Home.route) }
            )
        }

        composable(route = AuthRoutes.CreateAccount.route) {

            val viewModel: CreateAccountScreenViewModel = koinViewModel()

            CreateAccountScreen(
                viewModel = viewModel)
        }
    }
}

fun NavController.navigateToLogin() {
    this.navigate(route = AuthRoutes.Login.route)
}

fun NavController.navigateToCreateAccount() {
    this.navigate(route = AuthRoutes.CreateAccount.route)
}