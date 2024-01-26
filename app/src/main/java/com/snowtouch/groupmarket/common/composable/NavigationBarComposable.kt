package com.snowtouch.groupmarket.common.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snowtouch.groupmarket.R

sealed class NavBarItem(val icon: Int, val label: String, val route: String) {
    data object Home: NavBarItem(R.drawable.baseline_home_24, "Home", "HomeScreen")
    data object Groups: NavBarItem(R.drawable.baseline_group_24, "Groups", "GroupsScreen")
    data object Add: NavBarItem(R.drawable.baseline_add_circle_24, "New ad", "NewAdScreen")
    data object Messages: NavBarItem(R.drawable.baseline_email_24, "Messages", "MessagesScreen")
    data object Account: NavBarItem(R.drawable.baseline_account_circle_24, "Account", "AccountScreen")

    companion object {
        val allItems: List<NavBarItem> by lazy { listOf(Home, Groups, Add, Messages, Account) }
    }
}

@Composable
fun CompactScreenNavBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavBarItem.allItems.forEach{ item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                          navController.navigate(item.route) {
                              popUpToId
                              launchSingleTop = true
                          }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}

@Composable
fun BigScreenNavBar(navController: NavHostController) {
    NavigationRail {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        NavBarItem.allItems.forEach{ item ->
            NavigationRailItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpToId
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.label) },
                label = { Text(text = item.label) }
            )
        }
    }
}

@Preview
@Composable
fun CompactScreenNavBarPreview() {
    CompactScreenNavBar(navController = NavHostController(LocalContext.current))
}