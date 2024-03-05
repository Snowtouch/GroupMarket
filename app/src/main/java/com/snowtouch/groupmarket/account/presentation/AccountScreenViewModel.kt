package com.snowtouch.groupmarket.account.presentation

import com.snowtouch.groupmarket.core.presentation.util.SnackbarState
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel

class AccountScreenViewModel(
    private val authRepository: AuthRepository
): GroupMarketViewModel() {

    fun signOut(){
        launchCatching {
            authRepository.signOut()
            showSnackbar(SnackbarState.DEFAULT, "Successfully logged out")
        }
    }
}
