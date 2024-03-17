package com.snowtouch.groupmarket

import androidx.lifecycle.viewModelScope
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.core.presentation.GroupMarketViewModel

class MainViewModel(
    private val authRepository : AuthRepository,
) : GroupMarketViewModel() {

    init {
        getAuthState()
    }

    fun getAuthState() = authRepository.getAuthState(viewModelScope)

    val isEmailVerified get() = authRepository.currentUser?.isEmailVerified ?: false
}