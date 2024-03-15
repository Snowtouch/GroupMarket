package com.snowtouch.groupmarket.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource

@Composable
fun NavigationRail(
    onNavItemClick : (String) -> Unit
) {

    var currentRouteIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationRail {
        navMenu.forEachIndexed { index,navBarItem ->
            NavigationRailItem(
                selected = currentRouteIndex == index,
                onClick = {
                    currentRouteIndex = index
                    onNavItemClick(navBarItem.route)
                },
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