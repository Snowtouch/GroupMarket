package com.snowtouch.home_feature.domain.repository

import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Response

interface HomeRepository {

    suspend fun getLatestAdsPreview() : Response<List<AdvertisementPreview>>

    suspend fun getRecentlyViewedAdsPreview() : Response<List<AdvertisementPreview>>

    suspend fun getUserFavoriteAdsPreview() : Response<List<AdvertisementPreview>>
}