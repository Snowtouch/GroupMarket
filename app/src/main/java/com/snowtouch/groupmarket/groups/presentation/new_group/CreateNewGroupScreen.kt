package com.snowtouch.groupmarket.groups.presentation.new_group

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
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
import com.snowtouch.groupmarket.auth.presentation.components.ValidatedTextFieldOnCard
import com.snowtouch.groupmarket.core.presentation.components.CommonButton

@Composable
fun CreateNewGroupScreen(
    viewModel: CreateNewGroupScreenViewModel,
    onCreateGroupClick: () -> Unit
) {

    val uiState by viewModel.uiState

    CreateNewGroupScreenContent(
        uiState = uiState,
        onGroupNameChanged = viewModel::onNameChange,
        onGroupDescriptionChanged = viewModel::onDescriptionChange,
        onPrivacySwitchChange = { viewModel.onPrivateGroupSwitchChange() },
        onCreateGroupClick = {
            viewModel.createNewGroup()
            onCreateGroupClick() }
    )
}

@Composable
fun CreateNewGroupScreenContent(
    uiState: CreateNewGroupUiState,
    onGroupNameChanged: (String) -> Unit,
    onGroupDescriptionChanged: (String) -> Unit,
    onPrivacySwitchChange: () -> Unit,
    onCreateGroupClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ValidatedTextFieldOnCard(
            value = uiState.name,
            onNewValue = onGroupNameChanged,
            label = "Name",
            placeholder = "Enter group name")

        ValidatedTextFieldOnCard(
            modifier = Modifier.sizeIn(minHeight = 200.dp),
            value = uiState.description,
            onNewValue = onGroupDescriptionChanged,
            label = "Description",
            placeholder = "Enter group description")

        FilterChip(
            onClick = onPrivacySwitchChange,
            label = { Text("Private group") },
            selected = uiState.isPrivate,
            leadingIcon =
            if (uiState.isPrivate)
            { {
                Icon(
                    imageVector = Icons.Filled.Done,
                    contentDescription = "Done icon",
                    modifier = Modifier.size(FilterChipDefaults.IconSize)) }
            } else { null }
        )

        CommonButton(
            onClick = onCreateGroupClick,
            text = "Create group")
    }
}

@Preview
@Composable
fun ScreenPreview() {
    CreateNewGroupScreenContent(
        uiState = CreateNewGroupUiState(),
        onGroupNameChanged = {},
        onGroupDescriptionChanged = {},
        onPrivacySwitchChange = {},
        onCreateGroupClick = {})
}