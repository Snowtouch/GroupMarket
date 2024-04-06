package com.snowtouch.feature_groups.presentation.group_ads

import com.snowtouch.core.domain.model.AdvertisementPreview

internal sealed interface GroupAdsUiState {
    data object Loading : GroupAdsUiState
    data class Error(val e : Exception) : GroupAdsUiState
    data class Success(val groupAds : List<AdvertisementPreview>) : GroupAdsUiState

}