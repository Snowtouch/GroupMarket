package com.snowtouch.home_feature.presentation

import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetAdDetailsUseCase
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.domain.use_case.UpdateRecentlyViewedAdsListUseCase
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class HomeViewModel(
    private val homeRepository : HomeRepository,
    private val getAdDetailsUseCase : GetAdDetailsUseCase,
    private val getUserFavoriteAdsIdsFlowUseCase : GetUserFavoriteAdsIdsFlowUseCase,
    private val toggleFavoriteAdUseCase : ToggleFavoriteAdUseCase,
    private val updateRecentlyViewedAdsListUseCase : UpdateRecentlyViewedAdsListUseCase,
) : GroupMarketViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState(UiState.Loading))
    val homeUiState = _homeUiState.asStateFlow()

    private val _adDetailsUiState = MutableStateFlow(AdDetailsUiState())
    val adDetailsUiState = _adDetailsUiState.asStateFlow()

    init {
        getFavoritesIds()
        getRecentlyViewedAdvertisements()
        getNewAdvertisements()
        getFavoriteAdvertisements()
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching { toggleFavoriteAdUseCase.invoke(adId) }
    }

    fun

    fun updateSelectedAdId(adId : String) {
        _adDetailsUiState.update {
            it.copy(selectedAdId = adId)
        }
    }

    private fun getFavoritesIds() {
        launchCatching {
            getUserFavoriteAdsIdsFlowUseCase.invoke(viewModelScope).collect { result ->
                when (result) {
                    is Result.Loading -> _homeUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Failure -> _homeUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Success -> _homeUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            favoritesIdsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getAdvertisementDetails(adId : String) {
        launchCatching {
            _adDetailsUiState.update {
                it.copy(uiState = UiState.Loading, selectedAdId = adId)
            }

            when (val advertisementDetailsResult = getAdDetailsUseCase.invoke(adId)) {
                is Result.Loading -> _adDetailsUiState.update {
                    it.copy(uiState = UiState.Loading)
                }

                is Result.Failure -> _adDetailsUiState.update {
                    it.copy(uiState = UiState.Error(advertisementDetailsResult.e))
                }

                is Result.Success -> _adDetailsUiState.update {
                    it.copy(
                        uiState = UiState.Success,
                        adDetails = advertisementDetailsResult.data
                    )
                }
            }
        }
    }

    fun getNewAdvertisements() {
        launchCatching {
            homeRepository.getLatestAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _homeUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Loading -> _homeUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Success -> _homeUiState.update {
                        it.copy(uiState = UiState.Success, newAdsList = result.data ?: emptyList())
                    }
                }
            }
        }
    }

    fun getFavoriteAdvertisements() {
        launchCatching {
            homeRepository.getUserFavoriteAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _homeUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Loading -> _homeUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Success -> _homeUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            favoriteAdsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getRecentlyViewedAdvertisements() {
        launchCatching {
            homeRepository.getRecentlyViewedAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _homeUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Loading -> _homeUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Success -> _homeUiState.update {
                        it.copy(
                            uiState = UiState.Success,
                            recentlyViewedList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}