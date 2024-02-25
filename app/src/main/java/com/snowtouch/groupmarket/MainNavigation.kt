package com.snowtouch.groupmarket

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.snowtouch.groupmarket.screens.account.account
import com.snowtouch.groupmarket.screens.advertisement.advertisementDetailScreen
import com.snowtouch.groupmarket.screens.advertisement.navigateToAdvertisement
import com.snowtouch.groupmarket.screens.groups.userGroups
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
    isScreenSizeCompact: Boolean,
    isLoggedIn: Boolean
) {
    NavHost(navController = navController, startDestination = MainRoutes.Home.name) {

        composable(route = MainRoutes.Home.name) {

            val viewModel: HomeScreenViewModel = koinViewModel()

            HomeScreen(
                viewModel = viewModel,
                onNavigateToAdDetails = { advertisementId ->
                    navController.navigateToAdvertisement(
                        advertisementId = advertisementId
                    )
                }
            )
        }

        userGroups(
            navController = navController,
            isScreenSizeCompact = isScreenSizeCompact)

        composable(route = MainRoutes.NewAd.name) {

            val viewModel: NewAdvertisementScreenViewModel = koinViewModel()

            NewAdvertisementScreen(
                modifier = modifier,
                viewModel = viewModel
            )
        }

        composable(route = MainRoutes.Messages.name) {

            val viewModel: MessagesScreenViewModel = koinViewModel()

            MessagesScreen(
                viewModel = viewModel,
                onNavigateToConversation = { navController.navigate("conversationId") }
            )
        }

        account(
            navController = navController,
            isLoggedIn = isLoggedIn
        )

        advertisementDetailScreen(
            onNavigateBack = { navController.popBackStack() }
        )
    }
}