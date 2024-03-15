package com.snowtouch.groupmarket.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.snowtouch.groupmarket.account.navigation.accountFeature
import com.snowtouch.groupmarket.advertisement_details.navigation.advertisementDetailFeature
import com.snowtouch.groupmarket.auth.navigation.authFeature
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.groups.navigation.groupsFeature
import com.snowtouch.groupmarket.home.navigation.HomeRoute
import com.snowtouch.groupmarket.home.navigation.homeFeature
import com.snowtouch.groupmarket.messages.navigation.messagesFeature
import com.snowtouch.groupmarket.new_advertisement.navigation.newAdvertisementFeature

@Composable
fun MainNavigation(
    navController : NavHostController,
    displaySize : DisplaySize,
    isLoggedIn : Boolean
) {
    NavHost(navController = navController,startDestination = HomeRoute.Home.route) {

        homeFeature(navController = navController)

        groupsFeature(navController = navController, displaySize = displaySize)

        newAdvertisementFeature(navController = navController)

        messagesFeature(navController = navController)

        accountFeature(navController = navController, isLoggedIn = isLoggedIn)

        authFeature(navController = navController, displaySize = displaySize)

        advertisementDetailFeature(onNavigateBack = { navController.popBackStack() })
    }
}