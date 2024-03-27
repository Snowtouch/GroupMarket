package com.snowtouch.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.navigation.navMenuItems

@Composable
fun BottomNavigationBar(
    currentScreen : NavBarItem,
    onNavItemClick : (String) -> Unit,
) {

    NavigationBar {
        navMenuItems.forEachIndexed { index, navBarItem ->
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