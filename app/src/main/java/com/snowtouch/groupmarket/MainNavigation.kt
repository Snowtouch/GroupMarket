package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.groupmarket.screens.account.account
import com.snowtouch.groupmarket.screens.groups.groups
import com.snowtouch.groupmarket.screens.home.HomeScreen
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import com.snowtouch.groupmarket.screens.messages.MessagesScreen
import com.snowtouch.groupmarket.screens.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreen
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreenViewModel
import org.koin.androidx.compose.koinViewModel

enum class MainRoutes(val value: String) {
    Home("home"),
    Groups("groups"),
    NewAd("new_ad"),
    Messages("messages"),
    Account("account"),
}
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(navController = navController, startDestination = MainRoutes.Home.name) {

        composable(route = MainRoutes.Home.name) {

            val viewModel: HomeScreenViewModel = koinViewModel()

            HomeScreen(viewModel = viewModel) {
                navController.navigate("advertisement") {
                    popUpToId
                    launchSingleTop
                }
            }
        }

        groups(navController = navController)

        composable(route = MainRoutes.NewAd.name) {

            val viewModel: NewAdvertisementScreenViewModel = koinViewModel()

            NewAdvertisementScreen(viewModel = viewModel)
        }

        composable(route = MainRoutes.Messages.name) {

            val viewModel: MessagesScreenViewModel = koinViewModel()

            MessagesScreen(viewModel = viewModel) {
                navController.navigate("conversationId") {
                    popUpToId
                    launchSingleTop
                }
            }
        }

        account(navController, isLoggedIn)
    }
}