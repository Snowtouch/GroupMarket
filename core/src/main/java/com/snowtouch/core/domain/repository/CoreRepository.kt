package com.snowtouch.core.domain.repository

import kotlinx.coroutines.flow.Flow
import com.snowtouch.core.domain.model.Result

interface CoreRepository {
    val currentUserFavoriteAdsIds : Flow<List<String>>

    suspend fun toggleFavoriteAd(adId : String) : Result<Boolean>
}