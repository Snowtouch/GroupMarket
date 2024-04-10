package com.snowtouch.home_feature.presentation

import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class HomeViewModel(
    private val homeRepository : HomeRepository,
) : GroupMarketViewModel() {

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)
    internal val uiState = _uiState.asStateFlow()

    fun getLatestAdvertisements() {
        launchCatching {
            homeRepository.getLatestAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _uiState.update { HomeUiState.Error(result.e) }
                    is Result.Loading -> _uiState.update { HomeUiState.Loading }
                    is Result.Success -> _uiState.update {
                        HomeUiState.Success(
                            newAdsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getUserFavoriteAdvertisements() {
        launchCatching {
            homeRepository.getUserFavoriteAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _uiState.update { HomeUiState.Error(result.e) }
                    is Result.Loading -> _uiState.update { HomeUiState.Loading }
                    is Result.Success -> _uiState.update {
                        HomeUiState.Success(
                            userFavoritesList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }

    fun getUserRecentlyViewedAds() {
        launchCatching {
            homeRepository.getRecentlyViewedAdsPreview().collect { result ->
                when (result) {
                    is Result.Failure -> _uiState.update { HomeUiState.Error(result.e) }
                    is Result.Loading -> _uiState.update { HomeUiState.Loading }
                    is Result.Success -> _uiState.update {
                        HomeUiState.Success(
                            recentlyWatchedAdsList = result.data ?: emptyList()
                        )
                    }
                }
            }
        }
    }
}