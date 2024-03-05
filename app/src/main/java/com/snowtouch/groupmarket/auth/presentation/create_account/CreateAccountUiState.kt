package com.snowtouch.groupmarket.auth.presentation.create_account

data class CreateAccountUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
