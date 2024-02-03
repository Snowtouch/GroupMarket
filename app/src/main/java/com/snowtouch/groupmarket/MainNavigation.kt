package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.groupmarket.screens.account.AccountScreen
import com.snowtouch.groupmarket.screens.account.AccountScreenViewModel
import com.snowtouch.groupmarket.screens.groups.GroupsScreen
import com.snowtouch.groupmarket.screens.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.screens.home.HomeScreen
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import com.snowtouch.groupmarket.screens.messages.MessagesScreen
import com.snowtouch.groupmarket.screens.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreen
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreenViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.koinViewModel

enum class MainRoutes(val value: String) {
    Home("home"),
    Groups("groups"),
    NewAd("new_ad"),
    Messages("messages"),
    Account("account")
}
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    NavHost(navController = navController, startDestination = MainRoutes.Home.name) {
        composable(MainRoutes.Home.name) {
            val viewModel: HomeScreenViewModel = koinViewModel()
            HomeScreen(viewModel) {
                navController.navigate("advertisement")
            }
        }
        composable(MainRoutes.Groups.name) {
            val viewModel: GroupsScreenViewModel = koinViewModel()
            GroupsScreen(viewModel) {
                navController.navigate("group")
            }
        }
        composable(MainRoutes.NewAd.name) {
            val viewModel: NewAdvertisementScreenViewModel = koinViewModel()
            NewAdvertisementScreen(viewModel = viewModel)
        }
        composable(MainRoutes.Messages.name) {
            val viewModel: MessagesScreenViewModel = koinViewModel()
            MessagesScreen(viewModel = viewModel) {
                navController.navigate("conversationId")
            }
        }
        composable(MainRoutes.Account.name) {
            val viewModel: AccountScreenViewModel = koinViewModel()
            AccountScreen(viewModel = viewModel)
        }
    }
}