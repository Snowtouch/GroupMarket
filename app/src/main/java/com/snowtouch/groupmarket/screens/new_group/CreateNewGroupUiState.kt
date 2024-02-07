package com.snowtouch.groupmarket.screens.new_group

data class CreateNewGroupUiState(
    val name: String = "",
    val description: String = "",
    val privateGroup: Boolean = false
)