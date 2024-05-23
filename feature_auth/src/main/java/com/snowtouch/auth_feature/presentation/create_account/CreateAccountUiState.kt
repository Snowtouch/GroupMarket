package com.snowtouch.auth_feature.presentation.create_account

internal data class CreateAccountUiState(
    val uiState : UiState = UiState.Idle,
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)

internal sealed interface UiState {
    data object Idle : UiState
    data object Loading : UiState
    data object Success : UiState
    data class Error(val e : Exception) : UiState
}