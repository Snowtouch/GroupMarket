package com.snowtouch.groupmarket.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.snowtouch.groupmarket.account.navigation.accountFeature
import com.snowtouch.groupmarket.advertisement_details.navigation.advertisementDetailFeature
import com.snowtouch.groupmarket.core.presentation.components.DisplaySize
import com.snowtouch.groupmarket.groups.navigation.groupsFeature
import com.snowtouch.groupmarket.home.navigation.homeFeature
import com.snowtouch.groupmarket.messages.navigation.messagesFeature
import com.snowtouch.groupmarket.new_advertisement.navigation.newAdvertisementFeature

@Composable
fun MainNavigation(
    modifier : Modifier = Modifier,
    navController : NavHostController,
    displaySize : DisplaySize,
    isLoggedIn : Boolean
) {
    NavHost(navController = navController,startDestination = MainScreenRoutes.Home.route) {

        homeFeature(navController = navController)

        groupsFeature(
            navController = navController,
            displaySize = displaySize
        )

        newAdvertisementFeature(navController = navController)

        messagesFeature(navController = navController)

        accountFeature(
            navController = navController,
            isLoggedIn = isLoggedIn
        )

        advertisementDetailFeature(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}