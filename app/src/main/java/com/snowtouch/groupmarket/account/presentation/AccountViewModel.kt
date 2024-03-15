package com.snowtouch.groupmarket.account.presentation

import com.snowtouch.groupmarket.account.domain.repository.AccountRepository
import com.snowtouch.groupmarket.core.domain.model.AdvertisementPreview
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AccountViewModel(
    private val accountRepository : AccountRepository,
) : GroupMarketViewModel() {

    private val _activeAdsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(Response.Loading)
    val activeAdsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _activeAdsResponse

    private val _finishedAdsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(Response.Loading)
    val finishedAdsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _finishedAdsResponse

    private val _draftsResponse =
        MutableStateFlow<Response<List<AdvertisementPreview>>>(Response.Loading)
    val draftsResponse : StateFlow<Response<List<AdvertisementPreview>>> = _draftsResponse

    fun signOut() {
        accountRepository.signOut()
    }

    fun getUserActiveAds() {
        launchCatching {
            _activeAdsResponse.value = Response.Loading
            _activeAdsResponse.value = accountRepository.getUserActiveAds()
        }
    }

    fun getUserFinishedAds() {
        launchCatching {
            _finishedAdsResponse.value = Response.Loading
            _finishedAdsResponse.value = accountRepository.getUserFinishedAds()
        }
    }

    fun getUserDrafts() {
        launchCatching {
            _draftsResponse.value = Response.Loading
            _draftsResponse.value = accountRepository.getUserDraftAds()
        }
    }
}

