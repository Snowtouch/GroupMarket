package com.snowtouch.feature_advertisement_details.presentation

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class AdvertisementDetailViewModel(
    private val  adDetailsRepository : AdDetailsRepository
) : GroupMarketViewModel() {

    private val _adDetailsResponse = MutableStateFlow<Response<Advertisement>>(Response.Loading)
    val adDetailsResponse : StateFlow<Response<Advertisement>> = _adDetailsResponse

    fun getAdvertisementDetails(adId: String) {
        launchCatching {
            _adDetailsResponse.value = Response.Loading
            _adDetailsResponse.value = adDetailsRepository.getAdDetails(adId)
        }
    }
}