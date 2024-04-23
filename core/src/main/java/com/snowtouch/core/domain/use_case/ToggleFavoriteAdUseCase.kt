package com.snowtouch.core.domain.use_case

import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository

class ToggleFavoriteAdUseCase(
    private val coreRepository : CoreRepository
) {
    suspend fun invoke(adId : String) : Result<Boolean> {
        return coreRepository.toggleFavoriteAd(adId)
    }
}