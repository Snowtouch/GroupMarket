package com.snowtouch.feature_advertisement_details.presentation

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class AdvertisementDetailViewModel(
    private val adDetailsRepository : AdDetailsRepository,
    private val coreRepository : CoreRepository,
) : GroupMarketViewModel() {

    private val _adDetailsResult = MutableStateFlow<Result<Advertisement>>(Result.Loading)
    val adDetailsResult : StateFlow<Result<Advertisement>> = _adDetailsResult

    val currentUserFavoriteAdsIds: Flow<List<String>> = coreRepository.currentUserFavoriteAdsIds

    fun getAdvertisementDetails(adId : String) {
        launchCatching {
            _adDetailsResult.value = Result.Loading
            _adDetailsResult.value = adDetailsRepository.getAdDetails(adId)
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            coreRepository.toggleFavoriteAd(adId)
        }
    }
}