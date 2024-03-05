package com.snowtouch.groupmarket.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.account.navigation.AccountRoutes
import com.snowtouch.groupmarket.groups.navigation.GroupsRoute
import com.snowtouch.groupmarket.home.navigation.HomeRoute
import com.snowtouch.groupmarket.messages.navigation.MessagesRoutes
import com.snowtouch.groupmarket.new_advertisement.navigation.NewAdRoute

data class NavBarItem(val icon: Int, val title: String, val route: String)

val navMenu = listOf(
    NavBarItem(R.drawable.baseline_home_24, "Home", HomeRoute.Home.route),
    NavBarItem(R.drawable.baseline_group_24, "Groups", GroupsRoute.Groups.route),
    NavBarItem(R.drawable.baseline_add_circle_24, "New ad", NewAdRoute.NewAd.route),
    NavBarItem(R.drawable.baseline_email_24, "Messages", MessagesRoutes.Messages.route),
    NavBarItem(R.drawable.baseline_account_circle_24, "Account", AccountRoutes.Account.route)
)

@Composable
fun BottomNavigationBar(
    onNavItemClick: (String) -> Unit
){
    var currentRouteIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        navMenu.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = currentRouteIndex == index,
                onClick = {
                    currentRouteIndex = index
                    onNavItemClick(navBarItem.route) },
                icon = {
                    Icon(
                        painterResource(id = navBarItem.icon),
                        contentDescription = navBarItem.title
                    )
                },
                label = { Text(text = navBarItem.title) }
            )
        }
    }
}