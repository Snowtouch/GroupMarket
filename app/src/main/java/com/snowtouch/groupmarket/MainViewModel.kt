package com.snowtouch.groupmarket

import androidx.lifecycle.viewModelScope
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel

class MainViewModel(
    private val authRepository: AuthRepository
): GroupMarketViewModel() {

    init {
        getAuthState()
    }

    fun getAuthState() = authRepository.getAuthState(viewModelScope)

    val isEmailVerified get() = authRepository.currentUser?.isEmailVerified ?: false
}