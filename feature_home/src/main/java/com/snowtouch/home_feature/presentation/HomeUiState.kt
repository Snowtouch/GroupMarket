package com.snowtouch.home_feature.presentation

import com.snowtouch.core.domain.model.AdvertisementPreview

internal sealed interface HomeUiState {
    data object Loading : HomeUiState
    data class Success(
        val newAdsList : List<AdvertisementPreview> = emptyList(),
        val userFavoritesList : List<AdvertisementPreview> = emptyList(),
        val recentlyWatchedAdsList : List<AdvertisementPreview> = emptyList(),
    ) : HomeUiState
    data class Error(val e : Exception) : HomeUiState
}