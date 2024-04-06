package com.snowtouch.home_feature.domain.repository

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getLatestAdsPreview() : Flow<Result<List<AdvertisementPreview>>>

    fun getRecentlyViewedAdsPreview() : Flow<Result<List<AdvertisementPreview>>>

    fun getUserFavoriteAdsPreview() : Flow<Result<List<AdvertisementPreview>>>
}