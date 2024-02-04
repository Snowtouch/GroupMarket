package com.snowtouch.groupmarket.screens.account

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.google.firebase.auth.FirebaseAuth
import com.snowtouch.groupmarket.MainRoutes
import com.snowtouch.groupmarket.screens.create_account.CreateAccountScreen
import com.snowtouch.groupmarket.screens.login.LoginScreen
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.java.KoinJavaComponent.inject
import org.koin.core.component.inject

const val accountRoute = "account"
const val loginRoute = "login"
const val createAccountRoute = "create_account"

fun NavGraphBuilder.accountGraph(
    navController: NavController
) {
    navigation(startDestination = MainRoutes.Account.name, route = accountRoute) {
        composable(MainRoutes.Account.name) {
            val viewModel: AccountScreenViewModel = koinViewModel()
            val auth = koinInject<FirebaseAuth>()

            if (auth.currentUser!=null)
                AccountScreen(
                    viewModel = viewModel,
                    onNavigateToOptionClick = {
                        navController.navigate(it)
                    })
            else
                LoginScreen(onCreateAccountClick = { navController.navigate("create_account") })
        }
        composable(loginRoute) {
            LoginScreen {
                navController.navigate(route = createAccountRoute)
            }
        }
        composable(createAccountRoute) {
            CreateAccountScreen()
        }
    }
}