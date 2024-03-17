package com.snowtouch.feature_new_advertisement.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementScreen
import com.snowtouch.feature_new_advertisement.presentation.NewAdvertisementViewModel
import org.koin.androidx.compose.koinViewModel

sealed class NewAdRoute(val route: String) {
    data object NewAd: NewAdRoute("Add")
}

fun NavGraphBuilder.newAdvertisementFeature(
    navController: NavHostController
) {
    navigation(startDestination = com.snowtouch.feature_new_advertisement.navigation.NewAdRoute.NewAd.route, route = "new_ad_feature") {
        composable(com.snowtouch.feature_new_advertisement.navigation.NewAdRoute.NewAd.route) {

            val viewmodel: NewAdvertisementViewModel = koinViewModel()

            NewAdvertisementScreen(
                viewModel = viewmodel,
                onPostAdvertisementClick = { navController.popBackStack() }
            )
        }
    }

}