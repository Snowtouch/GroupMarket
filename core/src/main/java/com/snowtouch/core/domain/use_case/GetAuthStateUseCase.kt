package com.snowtouch.core.domain.use_case

import com.snowtouch.core.domain.repository.CoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

class GetAuthStateUseCase(private val coreRepository : CoreRepository) {

    fun invoke(viewModelScope : CoroutineScope) : StateFlow<Boolean> {
        return coreRepository.getAuthState(viewModelScope)
    }
}