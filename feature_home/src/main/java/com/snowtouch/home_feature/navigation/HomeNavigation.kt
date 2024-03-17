package com.snowtouch.home_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.home_feature.presentation.HomeScreen

sealed class HomeRoute(val route : String) {
    data object Home : HomeRoute("Home")
}

fun NavGraphBuilder.homeFeature(
    displaySize : DisplaySize,
    navController : NavHostController,
    navigateToAdDetails : (String) -> Unit,
) {
    navigation(startDestination = HomeRoute.Home.route, route = "home_feature") {
        composable(route = HomeRoute.Home.route) {

            HomeScreen(
                displaySize = displaySize,
                navigateToAdDetails = { advertisementId -> navigateToAdDetails(advertisementId) },
                onBottomBarIconClick = { route -> navController.navigate(route) }
            )
        }
    }
}

fun NavController.navigateToHome() {
    this.navigate(route = HomeRoute.Home.route)
}