package com.snowtouch.auth_feature.presentation.create_account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.auth_feature.presentation.components.isValidEmail
import com.snowtouch.auth_feature.presentation.components.isValidLength
import com.snowtouch.auth_feature.presentation.components.isValidPassword
import com.snowtouch.auth_feature.presentation.components.passwordMatches
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState

class CreateAccountViewModel(
    private val authRepository: AuthRepository
) : GroupMarketViewModel() {

    var signUpResult by mutableStateOf<Result<Boolean>>(Result.Success(false))
        private set
    var sendEmailVerificationResult by mutableStateOf<Result<Boolean>>(Result.Success(false))
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
            signUpResult = Result.Loading
            signUpResult = authRepository.checkIfUserNameExists(name)
            when (val response = signUpResult) {
                is Result.Success ->
                    signUpResult = authRepository.createAccountWithEmailAndPassword(email, password, name)
                is Result.Failure ->
                    showSnackbar(SnackbarState.ERROR, response.e.localizedMessage?: "Error")
                is Result.Loading -> Unit
            }
        }
    }

    fun sendVerificationEmail() {
        launchCatching{
            sendEmailVerificationResult = Result.Loading
            sendEmailVerificationResult = authRepository.sendVerificationEmail()
            showSnackbar(SnackbarState.DEFAULT, "Verification e-mail sent.")
        }
    }
}
