package com.snowtouch.feature_advertisement_details.presentation

import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.use_case.GetUserFavoriteAdsIdsFlowUseCase
import com.snowtouch.core.domain.use_case.ToggleFavoriteAdUseCase
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map

internal class AdvertisementDetailViewModel(
    private val adDetailsRepository : AdDetailsRepository,
    getUserFavoriteAdsIdsFlowUseCase : GetUserFavoriteAdsIdsFlowUseCase,
    private val toggleFavoriteAdUseCase : ToggleFavoriteAdUseCase,
) : GroupMarketViewModel() {

    private val _adDetailsResult = MutableStateFlow<Result<Advertisement>>(Result.Loading)
    val adDetailsResult : StateFlow<Result<Advertisement>> = _adDetailsResult

    val currentUserFavoriteAdsIds = getUserFavoriteAdsIdsFlowUseCase.invoke(viewModelScope).map { result ->
        when (result) {
            is Result.Failure -> TODO()
            is Result.Loading -> TODO()
            is Result.Success -> TODO()
        }
    }

    fun getAdvertisementDetails(adId : String) {
        launchCatching {
            _adDetailsResult.value = Result.Loading
            _adDetailsResult.value = adDetailsRepository.getAdDetails(adId)
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            toggleFavoriteAdUseCase.invoke(adId)
        }
    }
}