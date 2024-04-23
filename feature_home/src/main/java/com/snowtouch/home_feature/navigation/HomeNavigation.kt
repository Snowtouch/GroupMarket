package com.snowtouch.home_feature.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.home_feature.presentation.HomeScreen
import com.snowtouch.home_feature.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel

sealed class HomeRoute(val route : String) {
    data object HomeFeature : HomeRoute("home_feature")
    data object Home : HomeRoute("Home")
}

fun NavGraphBuilder.homeFeature(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navController : NavHostController,
    navigateToAdDetails : (String) -> Unit,
) {
    navigation(startDestination = HomeRoute.Home.route, route = HomeRoute.HomeFeature.route) {
        composable(route = HomeRoute.Home.route) {

            val viewModel : HomeViewModel = koinViewModel()

            HomeScreen(
                currentScreen = currentScreen,
                displaySize = displaySize,
                navigateToAdDetails = { advertisementId -> navigateToAdDetails(advertisementId) },
                onNavMenuItemClick = { route -> navController.navigate(route) },
                viewModel = viewModel
            )
        }
    }
}

fun NavController.navigateToHome() {
    this.navigate(route = HomeRoute.HomeFeature.route)
}