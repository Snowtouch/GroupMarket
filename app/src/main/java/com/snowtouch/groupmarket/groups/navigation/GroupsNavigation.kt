package com.snowtouch.groupmarket.groups.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.groupmarket.advertisement_details.navigation.navigateToAdvertisement
import com.snowtouch.groupmarket.core.presentation.components.DisplaySize
import com.snowtouch.groupmarket.groups.presentation.group_ads.GroupAdsScreen
import com.snowtouch.groupmarket.groups.presentation.group_ads.GroupAdsScreenViewModel
import com.snowtouch.groupmarket.groups.presentation.groups.GroupsScreen
import com.snowtouch.groupmarket.groups.presentation.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.groups.presentation.new_group.CreateNewGroupScreen
import com.snowtouch.groupmarket.groups.presentation.new_group.CreateNewGroupScreenViewModel
import org.koin.androidx.compose.koinViewModel

sealed class GroupsRoute(val route: String) {
    data object Groups: GroupsRoute("groups")
    data object GroupAds: GroupsRoute("groupAdsScreen")
    data object NewGroup: GroupsRoute("new_group")
}

fun NavGraphBuilder.groupsFeature(
    navController: NavController,
    displaySize : DisplaySize
) {
    navigation(startDestination = GroupsRoute.Groups.route, route = "groupsGraph") {
        composable(GroupsRoute.Groups.route) {

            val viewModel: GroupsScreenViewModel = koinViewModel()

            GroupsScreen(
                viewModel = viewModel,
                displaySize = displaySize,
                onBottomBarIconClick = { route ->
                    navController.navigate(route) },
                navigateToGroupAdsScreen = { groupId ->
                    navController.navigateToGroupAds(groupId) },
                navigateToNewGroupScreen = {
                    navController.navigateToNewGroup() },
                navigateToAdDetailsScreen = { adId ->
                    navController.navigateToAdvertisement(adId)
                }
            )
        }

        composable(
            route = GroupsRoute.GroupAds.route,
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
        
        composable(GroupsRoute.NewGroup.route) {

            val viewModel: CreateNewGroupScreenViewModel = koinViewModel()

            CreateNewGroupScreen(
                viewModel = viewModel,
                onCreateGroupClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavController.navigateToGroups() {
    this.navigate(route = GroupsRoute.Groups.route)
}

fun NavController.navigateToGroupAds(groupId: String) {
    this.navigate(route = GroupsRoute.GroupAds.route + "/$groupId")
}

fun NavController.navigateToNewGroup() {
    this.navigate(route = GroupsRoute.NewGroup.route)
}