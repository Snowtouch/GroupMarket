package com.snowtouch.feature_new_advertisement.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.feature_new_advertisement.presentation.advertisement_post_result.AdvertisementPostResult
import com.snowtouch.feature_new_advertisement.presentation.new_advertisement.NewAdvertisementScreen

sealed class NewAdRoute(val route : String) {
    data object NewAdFeature : NewAdRoute("new_ad_feature")
    data object NewAd : NewAdRoute("Add")
    data object PostResult : NewAdRoute("post_result")
}

fun NavGraphBuilder.newAdvertisementFeature(
    navigateToHome : () -> Unit,
    navigateToAdDetails : (String) -> Unit,
    navController : NavHostController,
) {
    navigation(startDestination = NewAdRoute.NewAd.route, route = NewAdRoute.NewAdFeature.route) {
        composable(NewAdRoute.NewAd.route) {
            NewAdvertisementScreen(
                navigateBack = { navController.popBackStack() },
                navigateToPostResult = { navController.navigate(NewAdRoute.PostResult.route) }
            )
        }

        composable(
            route = NewAdRoute.PostResult.route + "/{advertisementId}",
            arguments = listOf(
                navArgument(name = "advertisementId") {
                    type = NavType.StringType
                }
            )
        ) {
            val advertisementId = it.arguments?.getString("advertisementId") ?: ""

            AdvertisementPostResult(
                navigateToHome = navigateToHome,
                navigateToAdDetails = { navigateToAdDetails(advertisementId) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }

}