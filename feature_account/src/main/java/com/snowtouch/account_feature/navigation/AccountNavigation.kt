package com.snowtouch.account_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.account_feature.presentation.account.AccountScreen
import com.snowtouch.core.presentation.util.DisplaySize

sealed class AccountRoutes(val route : String) {
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
    displaySize : DisplaySize,
    navController : NavController,
    isLoggedIn : Boolean,
    notSignedInDestination : String,
    navigateToLoginScreen : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
) {
    val startDestination =
        when (isLoggedIn) {
            true -> AccountRoutes.Account.route
            false -> notSignedInDestination
        }

    navigation(startDestination = startDestination, route = "account_feature") {

        composable(AccountRoutes.Account.route) {

            AccountScreen(
                displaySize = displaySize,
                navigateBack = { navController.popBackStack() },
                navigateToLogin = navigateToLoginScreen,
                navigateToAccountOption = { optionRoute ->
                    navController.navigate(optionRoute)
                },
                navigateToAdDetails = navigateToAdDetails,
                onNavBarIconClick = { route ->
                    navController.navigate(route) {
                        popUpToRoute
                    }
                },
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