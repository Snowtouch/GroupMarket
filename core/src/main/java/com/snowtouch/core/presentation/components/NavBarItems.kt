package com.snowtouch.core.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val title : String
)

val navMenu = listOf(
    NavBarItem(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        title = "Home"
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.Groups,
        unselectedIcon = Icons.Outlined.Groups,
        title = "Groups"
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircle,
        title = "Add"
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        title = "Messages"
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        title = "Account"
    )
)