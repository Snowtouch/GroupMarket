package com.snowtouch.groupmarket.common.composable

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.snowtouch.groupmarket.MainRoutes
import com.snowtouch.groupmarket.R

data class NavBarItem(val icon: Int, val title: String, val route: String)

val navMenu = arrayOf(
    NavBarItem(R.drawable.baseline_home_24, "Home", MainRoutes.Home.name),
    NavBarItem(R.drawable.baseline_group_24, "Groups", MainRoutes.Groups.name),
    NavBarItem(R.drawable.baseline_add_circle_24, "New ad", MainRoutes.NewAd.name),
    NavBarItem(R.drawable.baseline_email_24, "Messages", MainRoutes.Messages.name),
    NavBarItem(R.drawable.baseline_account_circle_24, "Account", MainRoutes.Account.name)
)
@Composable
fun CompactScreenNavBar(navController: NavHostController) {
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navMenu.forEach{
            NavigationBarItem(
                selected = currentRoute == it.route,
                onClick = {
                          navController.navigate(it.route) {
                              popUpToId
                              launchSingleTop = true
                          }
                },
                icon = { Icon(painterResource(id = it.icon), contentDescription = it.title) },
                label = { Text(text = it.title) }
            )
        }
    }
}

@Composable
fun BigScreenNavBar(navController: NavHostController) {
    NavigationRail {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        navMenu.forEach{
            NavigationRailItem(
                selected = currentRoute == it.route,
                onClick = {
                    navController.navigate(it.route) {
                        popUpToId
                        launchSingleTop = true
                    }
                },
                icon = { Icon(painterResource(id = it.icon), contentDescription = it.title) },
                label = { Text(text = it.title) }
            )
        }
    }
}

@Preview
@Composable
fun CompactScreenNavBarPreview() {
    CompactScreenNavBar(navController = NavHostController(LocalContext.current))
}