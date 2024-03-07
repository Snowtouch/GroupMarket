package com.snowtouch.groupmarket.auth.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.auth.presentation.components.isValidEmail
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.domain.model.Response.Loading
import com.snowtouch.groupmarket.core.domain.model.Response.Success
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.core.presentation.util.SnackbarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginScreenViewModel(private val authRepository: AuthRepository): GroupMarketViewModel() {

    private val _loginResponse = MutableStateFlow<Response<Boolean>>(Success(false))
    val loginResponse: StateFlow<Response<Boolean>> = _loginResponse

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
            _loginResponse.value = Loading
            _loginResponse.value = authRepository.loginWithEmailAndPassword(email, password)
            showSnackbar(SnackbarState.DEFAULT,"Logged in successfully")
        }
    }
}