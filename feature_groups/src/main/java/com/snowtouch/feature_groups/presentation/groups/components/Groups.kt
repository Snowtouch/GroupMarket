package com.snowtouch.feature_groups.presentation.groups.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.core.presentation.components.Loading
import com.snowtouch.core.presentation.components.LoadingFailed
import com.snowtouch.core.presentation.util.DisplaySize
import com.snowtouch.feature_groups.presentation.GroupsViewModel
import com.snowtouch.feature_groups.presentation.groups.GroupsUiState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun Groups(
    displaySize : DisplaySize,
    onGoToGroupAdsClick: (String) -> Unit,
    onAdvertisementCardClick:  (String) -> Unit,
    modifier : Modifier = Modifier,
    viewModel: GroupsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is GroupsUiState.Loading -> Loading(modifier = modifier)

        is GroupsUiState.Success ->
            GroupsContent(
                userGroupsList = (uiState as GroupsUiState.Success).groupsPreviewList,
                displaySize = displaySize,
                onGoToGroupAdsClick = onGoToGroupAdsClick,
                onAdvertisementCardClick = onAdvertisementCardClick,
                modifier = modifier
            )

        is GroupsUiState.Error -> LoadingFailed(
            canRefresh = true,
            onErrorIconClick = viewModel::getUserGroupsData,
            modifier = modifier,
            errorMessage = (uiState as GroupsUiState.Error).e?.localizedMessage
        )
    }
}