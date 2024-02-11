package com.snowtouch.groupmarket.screens.account

import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class AccountScreenViewModel(
    private val accountService: AccountService
): GroupMarketViewModel() {

    fun signOut(){
        launchCatching {
            accountService.signOut()
            showSnackbar(SnackbarState.DEFAULT, "Successfully logged out")
        }
    }
}
