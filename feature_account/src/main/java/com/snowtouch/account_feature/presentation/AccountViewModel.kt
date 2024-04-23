package com.snowtouch.account_feature.presentation

import android.util.Log
import com.snowtouch.account_feature.domain.repository.AccountRepository
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import com.snowtouch.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal class AccountViewModel(
    private val accountRepository : AccountRepository,
    private val coreRepository : CoreRepository,
) : GroupMarketViewModel() {

    private val _activeAdsResult =
        MutableStateFlow<Result<List<AdvertisementPreview>>>(
            Result.Loading)
    val activeAdsResult : StateFlow<Result<List<AdvertisementPreview>>> = _activeAdsResult

    private val _finishedAdsResult =
        MutableStateFlow<Result<List<AdvertisementPreview>>>(
            Result.Loading)
    val finishedAdsResult : StateFlow<Result<List<AdvertisementPreview>>> = _finishedAdsResult

    private val _draftsResult =
        MutableStateFlow<Result<List<AdvertisementPreview>>>(
            Result.Loading)
    val draftsResult : StateFlow<Result<List<AdvertisementPreview>>> = _draftsResult

    //TODO() val currentUserFavoriteAdsIds: Flow<List<String>> = coreRepository.currentUserFavoriteAdsIds

    fun signOut() {
        accountRepository.signOut()
    }

    fun getUserActiveAds() {
        launchCatching {
            _activeAdsResult.value = Result.Loading
            _activeAdsResult.value = accountRepository.getUserActiveAds()
            Log.e("getUserActiveAds", "run")
        }
    }

    fun toggleFavoriteAd(adId : String) {
        launchCatching {
            coreRepository.toggleFavoriteAd(adId)
        }
    }

    fun getUserFinishedAds() {
        launchCatching {
            _finishedAdsResult.value = Result.Loading
            _finishedAdsResult.value = accountRepository.getUserFinishedAds()
        }
    }

    fun getUserDrafts() {
        launchCatching {
            _draftsResult.value = Result.Loading
            _draftsResult.value = accountRepository.getUserDraftAds()
        }
    }
}

