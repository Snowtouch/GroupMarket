package com.snowtouch.home_feature.presentation

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.home_feature.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val homeRepository : HomeRepository,
) : GroupMarketViewModel() {

    private val _newAdsDataResponse = MutableStateFlow<Response<List<AdvertisementPreview>>>(
        Response.Loading
    )
    val newAdsDataResponse : StateFlow<Response<List<AdvertisementPreview>>> = _newAdsDataResponse

    private val _favoriteAdsDataResponse = MutableStateFlow<Response<List<AdvertisementPreview>>>(
        Response.Loading
    )
    val favoriteAdsDataResponse : StateFlow<Response<List<AdvertisementPreview>>> =
        _favoriteAdsDataResponse

    private val _recentlyViewedAdsDataResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(
            Response.Loading
        )
    val recentlyViewedAdsDataResponse : StateFlow<Response<List<AdvertisementPreview>>> =
        _recentlyViewedAdsDataResponse

    fun getUserFavoriteAdvertisements() {
        launchCatching {
            _favoriteAdsDataResponse.value = Response.Loading
            _favoriteAdsDataResponse.value = homeRepository.getUserFavoriteAdsPreview()
        }
    }

    fun getLatestAdvertisements() {
        launchCatching {
            _newAdsDataResponse.value = Response.Loading
            _newAdsDataResponse.value = homeRepository.getLatestAdsPreview()
        }
    }

    fun getUserRecentlyViewedAds() {
        launchCatching {
            _recentlyViewedAdsDataResponse.value = Response.Loading
            _recentlyViewedAdsDataResponse.value = homeRepository.getRecentlyViewedAdsPreview()
        }
    }
}