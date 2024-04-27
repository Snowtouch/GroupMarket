package com.snowtouch.feature_groups.presentation.groups.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.snowtouch.core.navigation.NavBarItem
import com.snowtouch.core.presentation.components.AdaptiveNavigationBar
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.components.SinglePageScaffold
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.group_ads.components.GroupAds
import com.snowtouch.feature_groups.presentation.groups.GroupAdsUiState
import com.snowtouch.feature_groups.presentation.groups.GroupsUiState
import com.snowtouch.feature_groups.presentation.groups.UiState

@Composable
internal fun Groups(
    displaySize : DisplaySize,
    currentScreen : NavBarItem,
    groupsUiState : GroupsUiState,
    groupAdsUiState : GroupAdsUiState,
    navigateToNewGroupScreen : () -> Unit,
    onNavMenuItemClick : (String) -> Unit,
    onGoToGroupAdsClick : (String) -> Unit,
    onAdvertisementCardClick : (String) -> Unit,
    onFavoriteButtonClick : (String) -> Unit,
    onSelectedGroupChanged : (String) -> Unit,
    modifier : Modifier = Modifier,
) {
    SinglePageScaffold(
        topBar = { GroupsTopAppBar(onCreateGroupClick = navigateToNewGroupScreen) },
        bottomBar = {
            AdaptiveNavigationBar(
                currentScreen = currentScreen,
                displaySize = displaySize,
                onNavMenuItemClick = onNavMenuItemClick
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .padding(start = if (displaySize == DisplaySize.Extended) 82.dp else 0.dp)
        ) {
            Box(modifier = Modifier.weight(0.6f)) {
                when (groupsUiState.uiState) {
                    is UiState.Loading -> Loading(modifier = modifier)

                    is UiState.Success -> GroupsContent(
                        userGroupsList = groupsUiState.userGroupsList,
                        onGoToGroupAdsClick = onGoToGroupAdsClick,
                        modifier = Modifier.padding(innerPadding)
                    )

                    is UiState.Error -> LoadingFailed(
                        canRefresh = false,
                        errorMessage = groupsUiState.uiState.e.localizedMessage
                    )
                }
            }
            if (displaySize == DisplaySize.Extended) {
                Box(modifier = Modifier.weight(0.4f)) {
                    LaunchedEffect(key1 = groupAdsUiState.selectedGroup) {
                        onSelectedGroupChanged(groupAdsUiState.selectedGroup)
                    }
                    GroupAds(
                        displaySize = displaySize,
                        groupAdsUiState = groupAdsUiState,
                        onAdvertisementCardClick = onAdvertisementCardClick,
                        onFavoriteButtonClick = onFavoriteButtonClick
                    )
                }
            }
        }
    }

}