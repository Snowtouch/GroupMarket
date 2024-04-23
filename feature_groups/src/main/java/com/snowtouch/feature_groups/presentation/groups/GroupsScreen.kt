package com.snowtouch.feature_groups.presentation.groups

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.groups.components.Groups

@Composable
internal fun GroupsScreen(
    viewModel : GroupsViewModel,
    displaySize : DisplaySize,
    currentScreen : NavBarItem,
    onNavMenuItemClick : (String) -> Unit,
    navigateToGroupAdsScreen : (String) -> Unit,
    navigateToNewGroupScreen : () -> Unit,
    navigateToAdDetailsScreen : (String) -> Unit,
) {
    val groupsUiState by viewModel.groupsUiState.collectAsStateWithLifecycle()
    val groupAdsUiState by viewModel.groupAdsUiState.collectAsStateWithLifecycle()

    Groups(
        displaySize = displaySize,
        currentScreen = currentScreen,
        groupsUiState = groupsUiState,
        groupAdsUiState = groupAdsUiState,
        onNavMenuItemClick = onNavMenuItemClick,
        navigateToNewGroupScreen = navigateToNewGroupScreen,
        onGoToGroupAdsClick = navigateToGroupAdsScreen,
        onAdvertisementCardClick = navigateToAdDetailsScreen,
        onFavoriteButtonClick = { adId -> viewModel.toggleFavoriteAd(adId) },
        onSelectedGroupChanged = { groupId -> viewModel.updateSelectedGroup(groupId) }
    )
}