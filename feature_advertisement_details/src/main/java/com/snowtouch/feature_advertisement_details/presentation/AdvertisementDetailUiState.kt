package com.snowtouch.feature_advertisement_details.presentation

import com.snowtouch.core.domain.model.Advertisement

internal data class AdDetailsUiState(
    val uiState : UiState = UiState.Loading,
    val advertisement : Advertisement = Advertisement(),
    val favoritesIdsList : List<String> = emptyList(),
)

internal sealed interface UiState {
    data object Loading : UiState
    data class Error(val e : Exception) : UiState
    data object Success : UiState
}
