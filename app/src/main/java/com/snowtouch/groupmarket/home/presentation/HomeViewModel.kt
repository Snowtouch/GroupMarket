package com.snowtouch.groupmarket.home.presentation

import com.snowtouch.groupmarket.core.domain.model.Advertisement
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.home.domain.repository.HomeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel(
    private val homeRepository : HomeRepository
) : GroupMarketViewModel() {

    private val _newAdsDataResponse = MutableStateFlow<Response<List<Advertisement>>>(Response.Loading)
    val newAdsDataResponse: StateFlow<Response<List<Advertisement>>> = _newAdsDataResponse

    private val _favoriteAdsDataResponse = MutableStateFlow<Response<List<Advertisement>>>(Response.Loading)
    val favoriteAdsDataResponse: StateFlow<Response<List<Advertisement>>> = _favoriteAdsDataResponse

    private val _recentlyViewedAdsDataResponse = MutableStateFlow<Response<List<Advertisement>>>(Response.Loading)
    val recentlyViewedAdsDataResponse: StateFlow<Response<List<Advertisement>>> = _recentlyViewedAdsDataResponse

    fun getUserFavoriteAdvertisements() {
        launchCatching {
            _favoriteAdsDataResponse.value = Response.Loading
            _favoriteAdsDataResponse.value = homeRepository.getUserFavoriteAdsPreview()
        }
    }

    fun getLatestAdvertisements() {
         launchCatching {
             _newAdsDataResponse.value = Response.Loading
             _newAdsDataResponse.value = homeRepository.getLatestAds()
        }
    }

    fun getUserRecentlyViewedAds() {
        launchCatching {
            _recentlyViewedAdsDataResponse.value = Response.Loading
            _recentlyViewedAdsDataResponse.value = homeRepository.getUserRecentlyViewedAds()
        }
    }
}