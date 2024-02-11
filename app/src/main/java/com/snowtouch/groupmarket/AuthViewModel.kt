package com.snowtouch.groupmarket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowtouch.groupmarket.model.service.AccountService

class AuthViewModel(
    private val accountService: AccountService
): ViewModel() {
    val currentUser = getAuthState()

    init {
        getAuthState()
    }

    private fun getAuthState() = accountService.getAuthState(viewModelScope)
}