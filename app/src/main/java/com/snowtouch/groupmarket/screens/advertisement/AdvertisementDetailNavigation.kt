package com.snowtouch.groupmarket.screens.advertisement

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import org.koin.androidx.compose.navigation.koinNavViewModel

fun NavGraphBuilder.advertisementDetailScreen(onNavigateBack: () -> Unit) {

    composable(
        route = "advertisement/{advertisementId}",
        arguments = listOf(
            navArgument("advertisementId") {
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