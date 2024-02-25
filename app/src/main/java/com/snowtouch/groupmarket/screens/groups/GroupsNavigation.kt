package com.snowtouch.groupmarket.screens.groups

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.groupmarket.MainRoutes
import com.snowtouch.groupmarket.screens.advertisement.navigateToAdvertisement
import com.snowtouch.groupmarket.screens.group_ads.GroupAdsScreen
import com.snowtouch.groupmarket.screens.group_ads.GroupAdsScreenViewModel
import com.snowtouch.groupmarket.screens.new_group.CreateNewGroupScreen
import com.snowtouch.groupmarket.screens.new_group.CreateNewGroupScreenViewModel
import org.koin.androidx.compose.koinViewModel

const val groupsRoute = "groups"
const val groupAdsRoute = "groupDetailsScreen/{groupId}"
const val newGroupRoute = "new_group"
fun NavGraphBuilder.userGroups(
    navController: NavController,
    isScreenSizeCompact: Boolean
) {
    navigation(startDestination = MainRoutes.Groups.name, route = "groupDetailsGraph") {
        composable(groupsRoute) {

            val viewModel: GroupsScreenViewModel = koinViewModel()

            GroupsScreen(
                viewModel = viewModel,
                isScreenSizeCompact = isScreenSizeCompact,
                navigateToGroupAdsScreen = { groupId ->
                    navController.navigate("groupDetailsScreen/$groupId") },
                navigateToNewGroupScreen = {
                    navController.navigate(newGroupRoute) },
                navigateToAdDetailsScreen = { adId ->
                    navController.navigateToAdvertisement(adId)
                }
            )
        }

        composable(
            route = groupAdsRoute,
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) {

            val groupId = it.arguments?.getString("groupId") ?: ""
            val viewModel: GroupAdsScreenViewModel = koinViewModel()

            GroupAdsScreen(
                viewModel = viewModel,
                groupId = groupId,
                navigateToAdDetailsScreen = { adId ->
                    navController.navigateToAdvertisement(adId)
                }
            )
        }
        
        composable(newGroupRoute) {
            val viewModel: CreateNewGroupScreenViewModel = koinViewModel()
            CreateNewGroupScreen(
                viewModel = viewModel,
                onCreateGroupClick = { navController.popBackStack() }
            )
        }
    }
}