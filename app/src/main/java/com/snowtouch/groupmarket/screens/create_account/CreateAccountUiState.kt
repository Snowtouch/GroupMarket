package com.snowtouch.groupmarket.screens.create_account

data class CreateAccountUiState(
    val email: String = "",
    val name: String = "",
    val password: String = "",
    val repeatPassword: String = ""
)
