package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.screens.account.AccountScreen
import com.snowtouch.groupmarket.screens.account.AccountScreenViewModel
import com.snowtouch.groupmarket.screens.advertisement.navigateToAdvertisement
import com.snowtouch.groupmarket.screens.groups.GroupsScreen
import com.snowtouch.groupmarket.screens.groups.GroupsScreenViewModel
import com.snowtouch.groupmarket.screens.home.HomeScreen
import com.snowtouch.groupmarket.screens.home.HomeScreenViewModel
import com.snowtouch.groupmarket.screens.home.homeRoute
import com.snowtouch.groupmarket.screens.home.homeScreen
import com.snowtouch.groupmarket.screens.messages.MessagesScreen
import com.snowtouch.groupmarket.screens.messages.MessagesScreenViewModel
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreen
import com.snowtouch.groupmarket.screens.new_advertisement.NewAdvertisementScreenViewModel
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.compose.navigation.koinNavViewModel
import org.koin.androidx.compose.viewModel

enum class MainRoutes(value: String) {
    Home("home"),
    Groups("groups"),
    NewAd("new_ad"),
    Messages("messages"),
    Account("account")
}
@Composable
fun MainNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    NavHost(navController = navController, startDestination = MainRoutes.Home.name) {
        composable(MainRoutes.Home.name) {
            val viewModel: HomeScreenViewModel = koinNavViewModel()
            HomeScreen(viewModel) {
                navController.navigate("advertisement")
            }
        }
        composable(MainRoutes.Groups.name) {
            val viewModel: GroupsScreenViewModel = koinNavViewModel()
            GroupsScreen(viewModel) {
                navController.navigate("group")
            }
        }
        composable(MainRoutes.NewAd.name) {
            val viewModel: NewAdvertisementScreenViewModel = koinNavViewModel()
            NewAdvertisementScreen(viewModel = viewModel)
        }
        composable(MainRoutes.Messages.name) {
            val viewModel: MessagesScreenViewModel = koinNavViewModel()
            MessagesScreen(viewModel = viewModel) {
                navController.navigate("conversationId")
            }
        }
        composable(MainRoutes.Account.name) {
            val viewModel: AccountScreenViewModel = koinNavViewModel()
            AccountScreen(viewModel = viewModel)
        }
    }
}