package com.snowtouch.groupmarket.screens.login

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.common.ext.isValidEmail
import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class LoginScreenViewModel(private val accountService: AccountService): GroupMarketViewModel() {
    var uiState = mutableStateOf(LoginUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }
    fun login(email: String, password: String) {
        if (!email.isValidEmail()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid e-mail address")
            return
        }
        if (password.isBlank()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid password")
            return
        }
        launchCatching {
            accountService.authenticate(email, password)
            showSnackbar(SnackbarState.DEFAULT,"Logged in successfully")
        }
    }
}