package com.snowtouch.groupmarket.screens.groups

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.snowtouch.groupmarket.MainRoutes
import com.snowtouch.groupmarket.screens.group_ads.GroupAdsScreen
import com.snowtouch.groupmarket.screens.new_group.CreateNewGroupScreen

const val groupsRoute = "groups"
const val groupAdsRoute = "groupDetailsScreen/{groupId}"
const val newGroupRoute = "new_group"
fun NavGraphBuilder.groupsGraph(
    navController: NavController
) {
    navigation(startDestination = MainRoutes.Groups.name, route = "groupDetailsGraph") {

        composable(groupsRoute) {
            GroupsScreen(
                navigateToGroupAdsScreen = { groupId ->
                    navController.navigate("groupDetailsScreen/$groupId") },
                navigateToNewGroupScreen = { navController.navigate(newGroupRoute) }
            )
        }

        composable(groupAdsRoute ,arguments = listOf(navArgument("groupId") { type = NavType.StringType })
        ) {
            backStackEntry ->
            val groupId = backStackEntry.arguments?.getString("groupId")
            GroupAdsScreen(groupId = groupId)
        }
        
        composable(newGroupRoute) {
            CreateNewGroupScreen()
        }
    }
}