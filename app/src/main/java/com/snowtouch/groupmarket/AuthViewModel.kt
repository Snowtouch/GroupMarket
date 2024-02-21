package com.snowtouch.groupmarket

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class AuthViewModel(
    private val accountService: AccountService,
    private val databaseService: DatabaseService
): GroupMarketViewModel() {
    val currentUser = getAuthState()

    init {
        getAuthState()
    }

    fun enableUserDataListener() {
        launchCatching {
            databaseService.enableUserDataListener()
        }
    }

    fun disableUserDataListener() {
        launchCatching {
            databaseService.disableUserDataListener()
        }
    }

    fun enableUserGroupsListener() {
        launchCatching {
            databaseService.enableUserGroupsDataListeners()
        }
    }

    private fun getAuthState() = accountService.getAuthState(viewModelScope)
}