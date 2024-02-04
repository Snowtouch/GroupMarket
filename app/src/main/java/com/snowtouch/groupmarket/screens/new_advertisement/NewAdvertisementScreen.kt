

package com.snowtouch.groupmarket.screens.new_advertisement

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun NewAdvertisementScreen(viewModel: NewAdvertisementScreenViewModel) {
    NewAdvertisementScreenContent()
}
@Composable
fun NewAdvertisementScreenContent() {
    AdCategoryDropdownMenu()
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdCategoryDropdownMenu(
    userGroupsList: List<String> = emptyList(),
    onUserGroupSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        }
    ) {
        TextField(
            value = groupName,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            placeholder = {
                Text(text = "Please select group")
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = modifier.menuAnchor()
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }
        ) {
            userGroupsList.forEach {
                DropdownMenuItem(
                    text = { it },
                    onClick = {
                        groupName = it
                        onUserGroupSelected(it)
                    }
                )
            }
        }
    }
}
@Preview
@Composable
fun NewAdScreenPreview() {
    NewAdvertisementScreenContent()
}