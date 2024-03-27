package com.snowtouch.account_feature.presentation

import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response
import com.snowtouch.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class AccountViewModel(
    private val accountRepository : AccountRepository,
) : GroupMarketViewModel() {

    private val _activeAdsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(
            Response.Loading(null))
    val activeAdsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _activeAdsResponse

    private val _finishedAdsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(
            Response.Loading(null))
    val finishedAdsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _finishedAdsResponse

    private val _draftsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(
            Response.Loading(null))
    val draftsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _draftsResponse

    fun signOut() {
        accountRepository.signOut()
    }

    fun getUserActiveAds() {
        launchCatching {
            _activeAdsResponse.value = Response.Loading(null)
            _activeAdsResponse.value = accountRepository.getUserActiveAds()
        }
    }

    fun getUserFinishedAds() {
        launchCatching {
            _finishedAdsResponse.value = Response.Loading(null)
            _finishedAdsResponse.value = accountRepository.getUserFinishedAds()
        }
    }

    fun getUserDrafts() {
        launchCatching {
            _draftsResponse.value = Response.Loading(null)
            _draftsResponse.value = accountRepository.getUserDraftAds()
        }
    }
}

