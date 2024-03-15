package com.snowtouch.groupmarket.groups.presentation.groups

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.groupmarket.core.presentation.components.BottomNavigationBar
import com.snowtouch.groupmarket.core.presentation.components.NavigationRail
import com.snowtouch.groupmarket.core.presentation.components.ScaffoldTemplate
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.groups.presentation.groups.components.Groups
import com.snowtouch.groupmarket.groups.presentation.groups.components.GroupsTopAppBar

@Composable
fun GroupsScreen(
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
                DisplaySize.Compact -> BottomNavigationBar(onNavItemClick = onNavBarIconClick)
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