package com.snowtouch.feature_new_advertisement.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.NewAdvertisementScreen
import org.koin.androidx.compose.koinViewModel

sealed class NewAdRoute(val route : String) {
    data object NewAdFeature : NewAdRoute("new_ad_feature")
    data object NewAd : NewAdRoute("Add")
}

fun NavGraphBuilder.newAdvertisementFeature(
    navigateToHome : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
    navController : NavHostController,
) {
    navigation(startDestination = NewAdRoute.NewAd.route, route = NewAdRoute.NewAdFeature.route) {
        composable(NewAdRoute.NewAd.route) {

            val viewModel : NewAdvertisementViewModel = koinViewModel()

            NewAdvertisementScreen(
                navigateBack = { navController.popBackStack() },
                navigateToHome = navigateToHome,
                navigateToAdvertisement = { adId -> navigateToAdDetails(adId) },
                viewModel = viewModel
            )
        }
    }

}