package com.snowtouch.home_feature.domain.repository

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getLatestAdsPreview(viewModelScope : CoroutineScope) : Flow<Result<List<AdvertisementPreview>>>

    fun getRecentlyViewedAdsPreview(viewModelScope : CoroutineScope) : Flow<Result<List<AdvertisementPreview>>>

    fun getUserFavoriteAdsPreview(viewModelScope : CoroutineScope) : Flow<Result<List<AdvertisementPreview>>>
}