package com.snowtouch.groupmarket.screens.new_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.snowtouch.groupmarket.common.composable.CardEmptyValidatedTextField
import org.koin.androidx.compose.koinViewModel

@Composable
fun CreateNewGroupScreen(
    viewModel: CreateNewGroupScreenViewModel = koinViewModel()
) {

    val uiState by viewModel.uiState

    CreateNewGroupScreenContent(
        uiState = uiState,
        onGroupNameChanged = viewModel::onNameChange,
        onGroupDescriptionChanged = viewModel::onDescriptionChange,
        onPrivacySwitchChange = { viewModel.onPrivateGroupSwitchChange() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNewGroupScreenContent(
    uiState: CreateNewGroupUiState,
    onGroupNameChanged: (String) -> Unit,
    onGroupDescriptionChanged: (String) -> Unit,
    onPrivacySwitchChange: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        CardEmptyValidatedTextField(
            value = uiState.name,
            onNewValue = onGroupNameChanged,
            label = "Name",
            placeholder = "Enter group name")

        CardEmptyValidatedTextField(
            modifier = Modifier.sizeIn(minHeight = 200.dp),
            value = uiState.description,
            onNewValue = onGroupDescriptionChanged,
            label = "Description",
            placeholder = "Enter group description")

        FilterChip(
            onClick = onPrivacySwitchChange,
            label = { Text("Private group") },
            selected = uiState.privateGroup,
            leadingIcon =
            if (uiState.privateGroup)
            { {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)) }
            } else { null }
        )
    }
}

@Preview
@Composable
fun ScreenPreview() {
    CreateNewGroupScreenContent(
        uiState = CreateNewGroupUiState(),
        onGroupNameChanged = {},
        onGroupDescriptionChanged = {},
        onPrivacySwitchChange = {})
}