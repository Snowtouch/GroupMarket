package com.snowtouch.home_feature.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetAdDetailsUseCase
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.domain.use_case.UpdateRecentlyViewedAdsListUseCase
import com.snowtouch.core.presentation.util.SnackbarGlobalDelegate
import com.snowtouch.core.presentation.util.SnackbarState
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    private val homeRepository : HomeRepository,
    private val getAdDetailsUseCase : GetAdDetailsUseCase,
    private val getUserFavoriteAdsIdsFlowUseCase : GetUserFavoriteAdsIdsFlowUseCase,
    private val toggleFavoriteAdUseCase : ToggleFavoriteAdUseCase,
    private val updateRecentlyViewedAdsListUseCase : UpdateRecentlyViewedAdsListUseCase,
    private val snackbar : SnackbarGlobalDelegate,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState(UiState.Loading))
    val homeUiState = _homeUiState.asStateFlow()

    private val _adDetailsUiState = MutableStateFlow(AdDetailsUiState())
    val adDetailsUiState = _adDetailsUiState.asStateFlow()

    fun toggleFavoriteAd(adId : String) {
        viewModelScope.launch {
            toggleFavoriteAdUseCase.invoke(adId).let { result ->
                when (result) {
                    is Result.Failure -> {
                        snackbar.showSnackbar(
                            state = SnackbarState.ERROR,
                            message = result.e.message ?: "Unknown error",
                            withDismissAction = false
                        )
                    }
                    else -> {}
                }
            }
        }
    }

    fun updateRecentlyViewedList(adId : String) {
        viewModelScope.launch { updateRecentlyViewedAdsListUseCase.invoke(adId) }
    }

    fun updateSelectedAdId(adId : String) {
        _adDetailsUiState.update {
            it.copy(selectedAdId = adId)
        }
    }

    fun getFavoriteAdvertisementsIds() {
        viewModelScope.launch {
            getUserFavoriteAdsIdsFlowUseCase.invoke().collect { result ->
                when (result) {
                    is Result.Loading -> _homeUiState.update {
                        it.copy(uiState = UiState.Loading)
                    }

                    is Result.Failure -> _homeUiState.update {
                        it.copy(uiState = UiState.Error(result.e))
                    }

                    is Result.Success -> {
                        _homeUiState.update {
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

    fun getAdvertisementDetails(adId : String) {
        viewModelScope.launch {
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
        viewModelScope.launch {
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

    fun getFavoriteAdvertisements(adsIds : List<String>) {
        viewModelScope.launch {
            homeRepository.getUserFavoriteAdsPreview(adsIds).collect { result ->
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
        viewModelScope.launch {
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