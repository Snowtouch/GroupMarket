package com.snowtouch.account_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.account_feature.presentation.account.AccountScreen
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.util.DisplaySize

sealed class AccountRoutes(val route : String) {
    data object AccountFeature : AccountRoutes("account_feature")
    data object Account : AccountRoutes("Account")
    data object ActiveAds : AccountRoutes("active_ads")
    data object DraftAds : AccountRoutes("draft_ads")
    data object FinishedAds : AccountRoutes("finished_ads")
    data object Settings : AccountRoutes("settings")
}

sealed class AccountSettingsRoutes(val route : String) {
    data object AccountData : AccountSettingsRoutes("account_data")
    data object ThemeSettings : AccountSettingsRoutes("theme_settings")
    data object AboutApp : AccountSettingsRoutes("about")
}

fun NavGraphBuilder.accountFeature(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navController : NavController,
    isLoggedIn : Boolean,
    navigateToLoginScreen : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
) {

    navigation(startDestination = AccountRoutes.Account.route, route = AccountRoutes.AccountFeature.route) {

        composable(AccountRoutes.Account.route) {
            if (!isLoggedIn) navigateToLoginScreen()
            AccountScreen(
                currentScreen = currentScreen,
                displaySize = displaySize,
                navigateBack = { navController.popBackStack() },
                navigateToLogin = navigateToLoginScreen,
                navigateToAccountOption = { optionRoute ->
                    navController.navigate(optionRoute)
                },
                navigateToAdDetails = navigateToAdDetails,
            ) { route ->
                navController.navigate(route) {
                    popUpToRoute
                }
            }
        }

        composable(AccountRoutes.ActiveAds.route) {

        }

        composable(AccountRoutes.FinishedAds.route) {

        }

        composable(AccountRoutes.DraftAds.route) {

        }

        composable(AccountRoutes.Settings.route) {

        }
    }
}

fun NavController.navigateToAccount() {
    this.navigate(route = AccountRoutes.Account.route)
}