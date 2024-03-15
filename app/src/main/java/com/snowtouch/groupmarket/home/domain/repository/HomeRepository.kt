package com.snowtouch.groupmarket.home.domain.repository

import com.snowtouch.groupmarket.core.domain.model.AdvertisementPreview
import com.snowtouch.groupmarket.core.domain.model.Response

interface HomeRepository {

    suspend fun getLatestAds() : Response<List<AdvertisementPreview>>

    suspend fun getRecentlyViewedAdsPreview() : Response<List<AdvertisementPreview>>

    suspend fun getUserFavoriteAdsPreview() : Response<List<AdvertisementPreview>>
}