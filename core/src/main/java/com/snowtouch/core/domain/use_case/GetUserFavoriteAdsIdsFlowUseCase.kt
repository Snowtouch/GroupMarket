package com.snowtouch.core.domain.use_case

import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

class GetUserFavoriteAdsIdsFlowUseCase(
    private val coreRepository : CoreRepository,
) {
    fun invoke(viewModelScope : CoroutineScope) : Flow<Result<List<String>>> {
        return coreRepository.getUserFavoriteAdIds(viewModelScope)
    }
}