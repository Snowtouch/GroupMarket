package com.snowtouch.auth_feature.presentation.login

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.auth_feature.presentation.components.isValidEmail
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.model.Result.Loading
import com.snowtouch.core.domain.model.Result.Success
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel(private val authRepository: AuthRepository): GroupMarketViewModel() {

    private val _loginResult = MutableStateFlow<Result<Boolean>>(Success(false))
    val loginResult: StateFlow<Result<Boolean>> = _loginResult

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
            _loginResult.value = Loading
            _loginResult.value = authRepository.loginWithEmailAndPassword(email, password)
        }
    }
}