package com.snowtouch.feature_groups.presentation.new_group

data class CreateNewGroupUiState(
    val name: String = "",
    val description: String = "",
    val isPrivate: Boolean = false
)