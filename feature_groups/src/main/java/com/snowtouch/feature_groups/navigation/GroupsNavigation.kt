package com.snowtouch.feature_groups.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.group_ads.GroupAdsScreen
import com.snowtouch.feature_groups.presentation.groups.GroupsScreen
import com.snowtouch.feature_groups.presentation.new_group.CreateNewGroupScreen
import org.koin.androidx.compose.koinViewModel

sealed class GroupsRoute(val route : String) {
    data object GroupsFeature : GroupsRoute("groups_feature")
    data object Groups : GroupsRoute("Groups")
    data object GroupAds : GroupsRoute("groupAdsScreen")
    data object NewGroup : GroupsRoute("new_group")
}

fun NavGraphBuilder.groupsFeature(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navController : NavController,
    navigateToAdDetails : (String) -> Unit,
) {
    navigation(
        startDestination = GroupsRoute.Groups.route,
        route = GroupsRoute.GroupsFeature.route
    ) {
        composable(GroupsRoute.Groups.route) {

            val viewModel : GroupsViewModel = koinViewModel()

            GroupsScreen(
                viewModel = viewModel,
                currentScreen = currentScreen,
                displaySize = displaySize,
                onNavMenuItemClick = { route ->
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
            route = GroupsRoute.GroupAds.route + "/{groupId}",
            arguments = listOf(
                navArgument(name = "groupId") { type = NavType.StringType }
            )
        ) {

            val groupId = it.arguments?.getString("groupId") ?: ""
            val viewModel : GroupsViewModel = koinViewModel()

            GroupAdsScreen(
                viewModel = viewModel,
                displaySize = displaySize,
                groupId = groupId,
                onNavigateBackClick = { navController.navigateUp() },
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
    this.navigate(route = "groupAdsScreen/$groupId")
}

fun NavController.navigateToNewGroup() {
    this.navigate(route = GroupsRoute.NewGroup.route)
}