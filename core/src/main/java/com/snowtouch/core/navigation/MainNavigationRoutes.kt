package com.snowtouch.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Groups
import androidx.compose.material.icons.outlined.Home
import androidx.compose.ui.graphics.vector.ImageVector

data class NavBarItem(
    val selectedIcon : ImageVector,
    val unselectedIcon : ImageVector,
    val featureRoute : String,
    val featureStartRoute : String,
    val title : String,
)

val navMenuItems = listOf(
    NavBarItem(
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        featureRoute = "home_feature",
        featureStartRoute = "Home",
        title = "Home",
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.Groups,
        unselectedIcon = Icons.Outlined.Groups,
        featureRoute = "groups_feature",
        featureStartRoute = "Groups",
        title = "Groups",
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.AddCircle,
        unselectedIcon = Icons.Outlined.AddCircleOutline,
        featureRoute = "new_ad_feature",
        featureStartRoute = "Add",
        title = "Add",
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.Email,
        unselectedIcon = Icons.Outlined.Email,
        featureRoute = "messages_feature",
        featureStartRoute = "Messages",
        title = "Messages",
    ),
    NavBarItem(
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        featureRoute = "account_feature",
        featureStartRoute = "Account",
        title = "Account",
    )
)