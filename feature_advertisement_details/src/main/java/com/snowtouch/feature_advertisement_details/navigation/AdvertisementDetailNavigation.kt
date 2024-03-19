package com.snowtouch.feature_advertisement_details.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.snowtouch.feature_advertisement_details.presentation.AdvertisementDetailScreen

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

        AdvertisementDetailScreen(
            advertisementId = advertisementId,
            navigateBack = onNavigateBack
        )
    }
}
fun NavController.navigateToAdvertisement(advertisementId: String) {
    this.navigate(route = "advertisement/$advertisementId")
}