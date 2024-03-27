package com.snowtouch.home_feature.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.BottomNavigationBar
import com.snowtouch.core.presentation.components.NavigationRail
import com.snowtouch.core.presentation.components.ScaffoldTemplate
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.home_feature.presentation.components.Home

@Composable
fun HomeScreen(
    currentScreen : NavBarItem,
    displaySize : DisplaySize,
    navigateToAdDetails: (String) -> Unit,
    onBottomBarIconClick: (String) -> Unit,
){
    ScaffoldTemplate(
        bottomBar = {
            when (displaySize) {
                DisplaySize.Compact -> BottomNavigationBar(
                    currentScreen = currentScreen,
                    onNavItemClick = onBottomBarIconClick
                )
                DisplaySize.Extended -> NavigationRail(onNavItemClick = onBottomBarIconClick) }
        }
    ) { innerPadding ->
        Home(
            displaySize = displaySize,
            onAdvertisementClick = navigateToAdDetails,
            onFavoriteButtonClick = { TODO() },
            modifier = Modifier.padding(innerPadding)
        )
    }
}
