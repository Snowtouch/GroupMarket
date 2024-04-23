package com.snowtouch.home_feature.presentation

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.AdvertisementPreview

internal data class HomeUiState(
    val uiState : UiState = UiState.Loading,
    val favoriteAdsList : List<AdvertisementPreview> = emptyList(),
    val newAdsList : List<AdvertisementPreview> = emptyList(),
    val recentlyViewedList : List<AdvertisementPreview> = emptyList(),
    val favoritesIdsList : List<String> = emptyList()
)

internal data class AdDetailsUiState(
    val uiState : UiState = UiState.Success,
    val selectedAdId : String? = null,
    val adDetails : Advertisement? = null,
)

internal sealed interface UiState {
    data object Loading : UiState
    data object Success : UiState
    data class Error(val e : Exception) : UiState
}