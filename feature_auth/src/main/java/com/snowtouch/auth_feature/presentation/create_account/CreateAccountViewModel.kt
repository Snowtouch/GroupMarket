package com.snowtouch.auth_feature.presentation.create_account

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.auth_feature.presentation.components.isValidEmail
import com.snowtouch.auth_feature.presentation.components.isValidLength
import com.snowtouch.auth_feature.presentation.components.isValidPassword
import com.snowtouch.auth_feature.presentation.components.passwordMatches
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.presentation.GroupMarketViewModel
import com.snowtouch.core.presentation.util.SnackbarState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class CreateAccountViewModel(
    private val authRepository : AuthRepository
) : GroupMarketViewModel() {

    private val _uiState = MutableStateFlow(CreateAccountUiState(uiState = UiState.Idle))
    val uiState = _uiState.asStateFlow()

    fun onEmailChange(newValue : String) {
        if (newValue.isValidLength())
            _uiState.update { it.copy(email = newValue) }
        else
            return
    }

    fun onNameChange(newValue : String) {
        if (newValue.isValidLength())
            _uiState.update { it.copy(name = newValue) }
        else
            return
    }

    fun onPasswordChange(newValue : String) {
        if (newValue.isValidLength())
            _uiState.update { it.copy(password = newValue) }
        else
            return
    }

    fun onRepeatPasswordChange(newValue : String) {
        if (newValue.isValidLength())
            _uiState.update { it.copy(repeatPassword = newValue) }
        else
            return
    }

    fun createAccount(email : String, name : String, password : String) {
        viewModelScope.launch {
            if (!verifyCredentials(email, name, password))
                return@launch
            _uiState.update { it.copy(uiState = UiState.Loading) }
            checkIfUserNameExists(name) { exists ->
                if (exists) {
                    showSnackbar(SnackbarState.ERROR, "Username already exists")
                    return@checkIfUserNameExists
                }
            }
            Log.d("CreateAccountViewModel", "createAccount: $email $name $password")
            val createAccountResult =
                authRepository.createAccountWithEmailAndPassword(
                    email = email,
                    password = password,
                    name = name
                )
            when (createAccountResult) {
                is Result.Failure -> {
                    _uiState.update { it.copy(uiState = UiState.Idle) }
                    showSnackbar(
                        SnackbarState.ERROR,
                        createAccountResult.e.message ?: "Unknown error"
                    )
                }

                is Result.Loading -> Unit
                is Result.Success -> {
                    _uiState.update { it.copy(uiState = UiState.Success) }
                    showSnackbar(SnackbarState.DEFAULT, "Account created successfully")
                }
            }
        }
    }

    private fun checkIfUserNameExists(name : String, onResult : (Boolean) -> Unit) {
        viewModelScope.launch {
            val checkIfUserNameExistsResult = async {
                authRepository.checkIfUserNameExists(name)
            }
            when (val result = checkIfUserNameExistsResult.await()) {
                is Result.Success -> {
                    onResult(result.data!!)
                }

                is Result.Failure -> {
                    showSnackbar(SnackbarState.ERROR, result.e.message ?: "Unknown error")
                    onResult(true)
                }

                else -> Unit
            }
        }
    }

    private fun verifyCredentials(email : String, name : String, password : String) : Boolean {
        if (!email.isValidEmail()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid e-mail address")
            return false
        }
        if (name.isBlank()) {
            showSnackbar(SnackbarState.ERROR, "Please enter valid name")
            return false
        }
        if (!password.isValidPassword()) {
            showSnackbar(SnackbarState.ERROR, "Please enter a valid password")
            return false
        }
        if (!password.passwordMatches(uiState.value.repeatPassword)) {
            showSnackbar(SnackbarState.ERROR, "Passwords do not match")
            return false
        }
        return true
    }
}
