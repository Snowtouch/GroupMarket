package com.snowtouch.groupmarket.screens.create_account

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.common.ext.isValidEmail
import com.snowtouch.groupmarket.common.ext.isValidPassword
import com.snowtouch.groupmarket.common.ext.passwordMatches
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.common.snackbar.SnackbarState
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class CreateAccountScreenViewModel(private val accountService: AccountService)
    : GroupMarketViewModel() {

    var uiState = mutableStateOf(CreateAccountUiState())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun onRepeatPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(repeatPassword = newValue)
    }

    fun createAccount(email: String, password: String) {
        if (!email.isValidEmail()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid e-mail address")
            return
        }
        if (!password.isValidPassword()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid password")
            return
        }
        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            showSnackbar(SnackbarState.ERROR, "Passwords do not match")
            return
        }

        Log.d("Account creation", "passed")
        launchCatching {
            accountService.createAccount(email, password)
        }
    }
}
