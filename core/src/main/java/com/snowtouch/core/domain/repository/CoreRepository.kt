package com.snowtouch.core.domain.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface CoreRepository {

    fun getAuthState(viewModelScope : CoroutineScope) : StateFlow<Boolean>

    fun getUserFavoriteAdIds() : Flow<Result<List<String>>>

    suspend fun updateRecentlyViewedAdsIds(adId : String) : Result<Boolean>

    suspend fun toggleFavoriteAd(adId : String) : Result<Boolean>

    suspend fun getAdDetails(adId : String) : Result<Advertisement>
}