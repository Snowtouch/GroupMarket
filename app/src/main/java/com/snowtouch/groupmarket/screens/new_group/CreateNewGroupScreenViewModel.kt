package com.snowtouch.groupmarket.screens.new_group

import androidx.compose.runtime.mutableStateOf
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel

class CreateNewGroupScreenViewModel(
    private val databaseService: DatabaseService
): GroupMarketViewModel() {

    var uiState = mutableStateOf(CreateNewGroupUiState())

    fun onNameChange(newValue: String) {
        uiState.value = uiState.value.copy(name = newValue)
    }

    fun onDescriptionChange(newValue: String) {
        uiState.value = uiState.value.copy(description = newValue)
    }

    fun onPrivateGroupSwitchChange() {
        uiState.value = uiState.value.copy(privateGroup = !uiState.value.privateGroup)
    }
}