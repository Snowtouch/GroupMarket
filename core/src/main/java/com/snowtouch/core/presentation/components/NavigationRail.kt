package com.snowtouch.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.snowtouch.core.navigation.navMenuItems

@Composable
fun NavigationRail(
    onNavItemClick : (String) -> Unit
) {

    var currentRouteIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationRail {
        navMenuItems.forEachIndexed { index, navBarItem ->
            NavigationRailItem(
                selected = currentRouteIndex == index,
                onClick = {
                    currentRouteIndex = index
                    onNavItemClick(navBarItem.featureRoute)
                },
                icon = {
                    Icon(
                        imageVector = if (index == currentRouteIndex) {
                            navBarItem.selectedIcon
                        } else navBarItem.unselectedIcon,
                        contentDescription = navBarItem.featureRoute
                    )
                },
                label = { Text(text = navBarItem.featureRoute) }
            )
        }
    }
}