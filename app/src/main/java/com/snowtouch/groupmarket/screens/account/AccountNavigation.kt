package com.snowtouch.groupmarket.screens.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.MainRoutes
import com.snowtouch.groupmarket.screens.create_account.CreateAccountScreen
import com.snowtouch.groupmarket.screens.login.LoginScreen
import com.snowtouch.groupmarket.screens.login.LoginScreenViewModel
import org.koin.androidx.compose.koinViewModel

const val auth = "auth"
const val accountOptionsScreen = "account"
const val loginScreen = "login"
const val createAccountScreen = "create_account"

fun NavGraphBuilder.account(
    navController: NavController,
    isLoggedIn: Boolean
) {
    val startDestination =
        when (isLoggedIn) {
            true -> accountOptionsScreen
            false -> loginScreen
        }

    navigation(startDestination = startDestination, route = MainRoutes.Account.name) {

        composable(accountOptionsScreen) {
            AccountScreen(
                onNavigateToOptionClick = { navController.navigate(it) },
                onSignOutNavigate = { navController.navigate(loginScreen) }
            )
        }

        composable(loginScreen) {
            val viewModel: LoginScreenViewModel = koinViewModel()
            LoginScreen(
                viewModel = viewModel,
                onCreateAccountClick = { navController.navigate(route = createAccountScreen) },
                onLoginButtonClick = {
                    navController.navigate(route = MainRoutes.Home.name) {
                        popUpTo(MainRoutes.Account.name) { inclusive = true }
                    }
                }
            )
        }

        composable(createAccountScreen) {
            CreateAccountScreen()
        }
    }
}