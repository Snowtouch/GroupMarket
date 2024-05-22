package com.snowtouch.feature_advertisement_details.presentation

import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class AdvertisementDetailViewModel(
    private val adDetailsRepository : AdDetailsRepository,
    private val getUserFavoriteAdsIdsFlowUseCase : GetUserFavoriteAdsIdsFlowUseCase,
    private val toggleFavoriteAdUseCase : ToggleFavoriteAdUseCase,
) : GroupMarketViewModel() {

    private val _adDetailsUiState = MutableStateFlow(AdDetailsUiState(UiState.Loading))
    val adDetailsUiState = _adDetailsUiState.asStateFlow()

    init {
        getFavoritesIds()
    }

    fun getAdvertisementDetails(adId : String) {
        launchCatching {
            _adDetailsUiState.update { it.copy(uiState = UiState.Loading) }
            when (val result = adDetailsRepository.getAdDetails(adId)) {
                is Result.Failure -> {
                    _adDetailsUiState.update { it.copy(uiState = UiState.Error(result.e)) }
                }

                is Result.Loading -> {
                    _adDetailsUiState.update { it.copy(uiState = UiState.Loading) }
                }

                is Result.Success -> {
                    _adDetailsUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            advertisement = result.data ?: Advertisement()
                        )
                    }
                }
            }
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            toggleFavoriteAdUseCase.invoke(adId)
        }
    }

    private fun getFavoritesIds() {
        launchCatching {
            getUserFavoriteAdsIdsFlowUseCase.invoke(viewModelScope).collect { result ->
                when (result) {
                    is Result.Loading -> _adDetailsUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Failure -> _adDetailsUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Success -> _adDetailsUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            favoritesIdsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

}