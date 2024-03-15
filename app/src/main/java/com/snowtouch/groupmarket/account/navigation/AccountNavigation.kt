package com.snowtouch.groupmarket.account.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.account.presentation.account.AccountScreen
import com.snowtouch.groupmarket.auth.navigation.AuthRoutes
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize

sealed class AccountRoutes(val route : String) {
    data object Account : AccountRoutes("account")
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
    displaySize : DisplaySize,
    navController : NavController,
    isLoggedIn : Boolean,
) {
    val startDestination =
        when (isLoggedIn) {
            true -> AccountRoutes.Account.route
            false -> AuthRoutes.Login.route
        }

    navigation(startDestination = startDestination, route = "auth") {

        composable(AccountRoutes.Account.route) {

            AccountScreen(
                displaySize = displaySize,
                navigateToAccountOption = { optionRoute ->
                    navController.navigate(optionRoute)
                },
                onNavBarIconClick = { route ->
                    navController.navigate(route) {
                        popUpToRoute
                    }
                },
                onSignOutNavigate = {
                    navController.navigate(AuthRoutes.Login.route) {
                        popUpTo(AuthRoutes.Login.route) { inclusive = true }
                    }
                }
            )
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