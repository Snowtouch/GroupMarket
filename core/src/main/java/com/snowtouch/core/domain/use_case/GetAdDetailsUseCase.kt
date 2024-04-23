package com.snowtouch.core.domain.use_case

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository

class GetAdDetailsUseCase(
    private val coreRepository : CoreRepository
) {
    suspend fun invoke(adId : String) : Result<Advertisement> {
        return coreRepository.getAdDetails(adId)
    }
}
