package com.snowtouch.core.presentation.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

@Composable
fun BottomNavigationBar(
    onNavItemClick : (String) -> Unit,
) {
    var currentRouteIndex by rememberSaveable { mutableIntStateOf(0) }

    NavigationBar {
        navMenu.forEachIndexed { index, navBarItem ->
            NavigationBarItem(
                selected = currentRouteIndex == index,
                onClick = {
                    currentRouteIndex = index
                    onNavItemClick(navBarItem.title)
                },
                icon = {
                    Icon(
                        imageVector = if (index == currentRouteIndex) {
                            navBarItem.selectedIcon
                        } else navBarItem.unselectedIcon,
                        contentDescription = navBarItem.title
                    )
                },
                label = { Text(text = navBarItem.title) }
            )
        }
    }
}