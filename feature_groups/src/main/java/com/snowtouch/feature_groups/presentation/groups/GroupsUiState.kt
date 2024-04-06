package com.snowtouch.feature_groups.presentation.groups

import com.snowtouch.feature_groups.domain.model.GroupPreview

internal sealed interface GroupsUiState {
    data object Loading : GroupsUiState
    data class Error(val e : Exception?) : GroupsUiState
    data class Success(val groupsPreviewList : List<GroupPreview>) : GroupsUiState
}