package com.snowtouch.groupmarket.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.advertisement_details.navigation.navigateToAdvertisement
import com.snowtouch.groupmarket.home.presentation.HomeScreen
import com.snowtouch.groupmarket.home.presentation.HomeViewModel
import org.koin.androidx.compose.koinViewModel

sealed class HomeRoute(val route: String) {
    data object Home: HomeRoute("home")
}
fun NavGraphBuilder.homeFeature(
    navController: NavHostController
) {
    navigation(startDestination = HomeRoute.Home.route, route = "home") {
        composable(route = HomeRoute.Home.route) {

            val viewModel: HomeViewModel = koinViewModel()

            HomeScreen(
                viewModel = viewModel,
                onNavigateToAdDetails = { advertisementId ->
                    navController.navigateToAdvertisement(advertisementId = advertisementId)
                }
            )
        }
    }
}

fun NavController.navigateToHome() {
    this.navigate(route = HomeRoute.Home.route)
}