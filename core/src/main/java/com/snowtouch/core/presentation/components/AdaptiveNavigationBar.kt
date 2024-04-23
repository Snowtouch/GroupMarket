package com.snowtouch.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.navigation.navMenuItems
import com.snowtouch.core.presentation.util.DisplaySize

@Composable
fun AdaptiveNavigationBar(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    onNavMenuItemClick : (String) -> Unit,
) {
    if (displaySize == DisplaySize.Compact) {
        BottomNavigationBar(
            currentScreen = currentScreen,
            onNavItemClick = onNavMenuItemClick
        )
    } else {
        NavigationRail(
            currentScreen = currentScreen,
            onNavItemClick = onNavMenuItemClick
        )
    }
}

@Composable
fun BottomNavigationBar(
    currentScreen : NavBarItem,
    onNavItemClick : (String) -> Unit,
) {

    NavigationBar {
        navMenuItems.forEach { navBarItem ->
            NavigationBarItem(
                selected = currentScreen == navBarItem,
                onClick = {
                    onNavItemClick(navBarItem.featureRoute)
                },
                icon = {
                    Icon(
                        imageVector = if (currentScreen == navBarItem) {
                            navBarItem.selectedIcon
                        } else navBarItem.unselectedIcon,
                        contentDescription = navBarItem.featureRoute
                    )
                },
                label = { Text(text = navBarItem.title) }
            )
        }
    }
}

@Composable
fun NavigationRail(
    currentScreen : NavBarItem,
    onNavItemClick : (String) -> Unit,
) {
    androidx.compose.material3.NavigationRail {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
                .offset(x = (-2).dp),
            verticalArrangement = Arrangement.spacedBy(12.dp, alignment = Alignment.Bottom)
        ) {
            navMenuItems.forEach { navBarItem ->
                NavigationRailItem(
                    selected = currentScreen == navBarItem,
                    onClick = { onNavItemClick(navBarItem.featureRoute) },
                    icon = {
                        Icon(
                            imageVector =
                            if (currentScreen == navBarItem) navBarItem.selectedIcon
                            else navBarItem.unselectedIcon,
                            contentDescription = navBarItem.featureRoute
                        )
                    },
                    label = { Text(text = navBarItem.title) }
                )
            }
        }
    }
}