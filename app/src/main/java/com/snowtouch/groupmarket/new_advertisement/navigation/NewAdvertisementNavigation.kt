package com.snowtouch.groupmarket.new_advertisement.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.snowtouch.groupmarket.new_advertisement.presentation.NewAdvertisementScreen
import com.snowtouch.groupmarket.new_advertisement.presentation.NewAdvertisementScreenViewModel
import org.koin.androidx.compose.koinViewModel

sealed class NewAdRoute(val route: String) {
    data object NewAd: NewAdRoute("new_ad")
}

fun NavGraphBuilder.newAdvertisementFeature(
    navController: NavHostController
) {
    navigation(startDestination = NewAdRoute.NewAd.route, route = "new_advertisement") {
        composable(NewAdRoute.NewAd.route) {

            val viewmodel: NewAdvertisementScreenViewModel = koinViewModel()

            NewAdvertisementScreen(
                viewModel = viewmodel,
                onPostAdvertisementClick = { navController.popBackStack() }
            )
        }
    }

}