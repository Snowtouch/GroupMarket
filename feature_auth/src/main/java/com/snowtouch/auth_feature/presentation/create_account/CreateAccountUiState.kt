package com.snowtouch.auth_feature.presentation.create_account

data class CreateAccountUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
