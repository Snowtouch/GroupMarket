package com.snowtouch.feature_groups.presentation.groups

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.GroupPreview

internal data class GroupsUiState(
    val uiState : UiState = UiState.Loading,
    val userGroupsList : List<GroupPreview> = emptyList(),
    val snackbarMessage : String = "",
)

internal data class GroupAdsUiState(
    val uiState : UiState = UiState.Success,
    val groupAds : List<AdvertisementPreview> = emptyList(),
    val favoritesList : List<String> = emptyList(),
    val selectedGroup : String = ""
)

internal sealed interface UiState {
    data object Loading : UiState
    data class Error(val e : Exception) : UiState
    data object Success : UiState
}