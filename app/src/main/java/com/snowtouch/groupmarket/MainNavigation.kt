package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snowtouch.account_feature.navigation.accountFeature
import com.snowtouch.auth_feature.navigation.AuthRoutes
import com.snowtouch.auth_feature.navigation.authFeature
import com.snowtouch.core.navigation.navMenuItems
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_advertisement_details.navigation.advertisementDetailFeature
import com.snowtouch.feature_advertisement_details.navigation.navigateToAdvertisement
import com.snowtouch.feature_groups.navigation.groupsFeature
import com.snowtouch.feature_messages.navigation.messagesFeature
import com.snowtouch.feature_new_advertisement.navigation.newAdvertisementFeature
import com.snowtouch.home_feature.navigation.HomeRoute
import com.snowtouch.home_feature.navigation.homeFeature
import com.snowtouch.home_feature.navigation.navigateToHome

@Composable
fun MainNavigation(
    navController : NavHostController,
    displaySize : DisplaySize,
    isLoggedIn : Boolean,
) {
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination

    val currentScreen =
        navMenuItems.find { it.featureStartRoute == currentDestination?.route } ?: navMenuItems[0]

    val startDestination =
        when (isLoggedIn) {
            true -> HomeRoute.HomeFeature.route
            false -> AuthRoutes.AuthFeature.route
        }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        homeFeature(
            currentScreen = currentScreen,
            displaySize = displaySize,
            navController = navController,
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        groupsFeature(
            currentScreen = currentScreen,
            displaySize = displaySize,
            navController = navController,
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        newAdvertisementFeature(
            navController = navController,
            navigateToHome = { navController.navigateToHome() },
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) },
        )

        messagesFeature(navController = navController)

        accountFeature(
            currentScreen = currentScreen,
            displaySize = displaySize,
            navController = navController,
            navigateToLoginScreen = { navController.navigate(AuthRoutes.AuthFeature.route) },
            navigateToAdDetails = { adId -> navController.navigateToAdvertisement(adId) }
        )

        authFeature(
            currentScreen = currentScreen,
            displaySize = displaySize,
            navController = navController
        )

        advertisementDetailFeature(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}