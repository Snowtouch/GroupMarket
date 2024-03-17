package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.snowtouch.account_feature.navigation.accountFeature
import com.snowtouch.auth_feature.navigation.AuthRoutes
import com.snowtouch.auth_feature.navigation.authFeature
import com.snowtouch.auth_feature.navigation.navigateToLogin
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.navigation.groupsFeature
import com.snowtouch.feature_advertisement_details.navigation.advertisementDetailFeature
import com.snowtouch.feature_advertisement_details.navigation.navigateToAdvertisement
import com.snowtouch.groupmarket.messages.navigation.messagesFeature
import com.snowtouch.feature_new_advertisement.navigation.newAdvertisementFeature
import com.snowtouch.home_feature.navigation.HomeRoute
import com.snowtouch.home_feature.navigation.homeFeature

@Composable
fun MainNavigation(
    navController : NavHostController,
    displaySize : DisplaySize,
    isLoggedIn : Boolean,
) {
    NavHost(
        navController = navController,
        startDestination = HomeRoute.Home.route
    ) {
        homeFeature(
            displaySize = displaySize,
            navController = navController,
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        groupsFeature(
            displaySize = displaySize,
            navController = navController,
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        newAdvertisementFeature(navController = navController)

        messagesFeature(navController = navController)

        accountFeature(
            displaySize = displaySize,
            navController = navController,
            isLoggedIn = isLoggedIn,
            notSignedInDestination = AuthRoutes.Login.route,
            navigateToLoginScreen = { navController.navigateToLogin() },
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        authFeature(
            displaySize = displaySize,
            navController = navController
        )

        advertisementDetailFeature(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}