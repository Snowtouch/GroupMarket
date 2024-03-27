package com.snowtouch.feature_groups.presentation.groups

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.BottomNavigationBar
import com.snowtouch.core.presentation.components.NavigationRail
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.groups.components.Groups
import com.snowtouch.feature_groups.presentation.groups.components.GroupsTopAppBar

@Composable
fun GroupsScreen(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    onNavBarIconClick : (String) -> Unit,
    navigateToGroupAdsScreen : (String) -> Unit,
    navigateToNewGroupScreen : () -> Unit,
    navigateToAdDetailsScreen : (String) -> Unit,
) {
    ScaffoldTemplate(
        topBar = { GroupsTopAppBar(onCreateGroupClick = navigateToNewGroupScreen) },
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavItemClick = onNavBarIconClick
                )
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onNavBarIconClick)
            }
        }
    ) { innerPadding ->
        Groups(
            displaySize = displaySize,
            onGoToGroupAdsClick = navigateToGroupAdsScreen,
            onAdvertisementCardClick = navigateToAdDetailsScreen,
            modifier = Modifier.padding(innerPadding)
        )
    }
}