package com.snowtouch.groupmarket.auth.presentation.create_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.auth.presentation.components.isValidEmail
import com.snowtouch.groupmarket.auth.presentation.components.isValidLength
import com.snowtouch.groupmarket.auth.presentation.components.isValidPassword
import com.snowtouch.groupmarket.auth.presentation.components.passwordMatches
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.domain.model.Response.Loading
import com.snowtouch.groupmarket.core.domain.model.Response.Success
import com.snowtouch.groupmarket.core.presentation.GroupMarketViewModel
import com.snowtouch.groupmarket.core.presentation.util.SnackbarState

class CreateAccountScreenViewModel(
    private val authRepository: AuthRepository
) : GroupMarketViewModel() {

    var signUpResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var sendEmailVerificationResponse by mutableStateOf<Response<Boolean>>(Success(false))
        private set
    var uiState = mutableStateOf(CreateAccountUiState())
        private set

    fun onEmailChange(newValue: String) {
        if (newValue.isValidLength())
            uiState.value = uiState.value.copy(email = newValue)
        else
            return
    }

    fun onNameChange(newValue: String) {
        if (newValue.isValidLength())
            uiState.value = uiState.value.copy(name = newValue)
        else
            return
    }

    fun onPasswordChange(newValue: String) {
        if (newValue.isValidLength())
            uiState.value = uiState.value.copy(password = newValue)
        else
            return
    }

    fun onRepeatPasswordChange(newValue: String) {
        if (newValue.isValidLength())
            uiState.value = uiState.value.copy(repeatPassword = newValue)
        else
            return
    }

    fun createAccount(email: String, name: String, password: String) {
        if (!email.isValidEmail()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid e-mail address")
            return
        }
        if (name.isBlank()) {
            showSnackbar(SnackbarState.ERROR, "Please enter valid name")
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
        launchCatching {
            signUpResponse = Loading
            signUpResponse = authRepository.checkIfUserNameExists(name)
            signUpResponse = authRepository.createAccountWithEmailAndPassword(email, password)
            showSnackbar(SnackbarState.DEFAULT, "Account successfully created")
        }
    }

    fun sendVerificationEmail() {
        launchCatching{
            sendEmailVerificationResponse = Loading
            sendEmailVerificationResponse = authRepository.sendVerificationEmail()
            showSnackbar(SnackbarState.DEFAULT, "Verification e-mail sent.")
        }
    }
}
