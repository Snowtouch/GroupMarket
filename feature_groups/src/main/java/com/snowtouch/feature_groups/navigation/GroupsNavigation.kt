package com.snowtouch.feature_groups.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.group_ads.GroupAdsScreen
import com.snowtouch.feature_groups.presentation.groups.GroupsScreen
import com.snowtouch.feature_groups.presentation.new_group.CreateNewGroupScreen

sealed class GroupsRoute(val route : String) {
    data object Groups : GroupsRoute("Groups")
    data object GroupAds : GroupsRoute("groupAdsScreen")
    data object NewGroup : GroupsRoute("new_group")
}

fun NavGraphBuilder.groupsFeature(
    displaySize : DisplaySize,
    navController : NavController,
    navigateToAdDetails : (String) -> Unit,
) {
    navigation(startDestination = GroupsRoute.Groups.route, route = "groups_feature") {
        composable(GroupsRoute.Groups.route) {

            GroupsScreen(
                displaySize = displaySize,
                onNavBarIconClick = { route ->
                    navController.navigate(route)
                },
                navigateToGroupAdsScreen = { groupId ->
                    navController.navigateToGroupAds(groupId)
                },
                navigateToNewGroupScreen = {
                    navController.navigateToNewGroup()
                },
                navigateToAdDetailsScreen = navigateToAdDetails
            )
        }

        composable(
            route = GroupsRoute.GroupAds.route,
            arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) {

            val groupId = it.arguments?.getString("groupId") ?: ""

            GroupAdsScreen(
                displaySize = displaySize,
                groupId = groupId,
                onNavigateBackClick = { navController.popBackStack() },
                navigateToAdDetailsScreen = navigateToAdDetails
            )
        }

        composable(GroupsRoute.NewGroup.route) {

            CreateNewGroupScreen(
                onNavigateBackClick = { navController.popBackStack() }
            )
        }
    }
}

fun NavController.navigateToGroupAds(groupId : String) {
    this.navigate(route = GroupsRoute.GroupAds.route + "/$groupId")
}

fun NavController.navigateToNewGroup() {
    this.navigate(route = GroupsRoute.NewGroup.route)
}