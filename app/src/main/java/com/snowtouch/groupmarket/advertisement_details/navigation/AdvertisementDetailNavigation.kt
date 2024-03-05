package com.snowtouch.groupmarket.advertisement_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.snowtouch.groupmarket.advertisement_details.presentation.AdvertisementDetailScreen
import com.snowtouch.groupmarket.advertisement_details.presentation.AdvertisementDetailScreenViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel

sealed class AdvertisementRoute(val route: String) {
    data object Advertisement: AdvertisementRoute("advertisement")
}

fun NavGraphBuilder.advertisementDetailFeature(onNavigateBack: () -> Unit) {

    composable(
        route = AdvertisementRoute.Advertisement.route + "/{advertisementId}",
        arguments = listOf(
            navArgument(name = "advertisementId") {
                type = NavType.StringType
            }
        )
    ) {
        val advertisementId = it.arguments?.getString("advertisementId") ?: ""
        val viewModel: AdvertisementDetailScreenViewModel = koinNavViewModel()

        AdvertisementDetailScreen(
            advertisementId = advertisementId,
            viewModel = viewModel,
            onNavigateBack = onNavigateBack
        )
    }
}
fun NavController.navigateToAdvertisement(advertisementId: String) {
    this.navigate(route = "advertisement/$advertisementId")
}