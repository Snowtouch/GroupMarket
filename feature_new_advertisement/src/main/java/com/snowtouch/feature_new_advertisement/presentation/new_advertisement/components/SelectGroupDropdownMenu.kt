package com.snowtouch.feature_new_advertisement.presentation.new_advertisement.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGroupDropdownMenu(
    userGroupsIdNameMapList : List<Map<String, String>>,
    onUserGroupSelected : (String) -> Unit,
    isError : Boolean,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue -> isExpanded = newValue },
        modifier = Modifier
    ) {
        TextField(
            value = groupName,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded) },
            placeholder = { Text(text = "Please select group") },
            supportingText = { if (isError) Text(text = "No group selected") },
            isError = isError,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor(),
            shape = MaterialTheme.shapes.medium
        )

        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier
        ) {
            userGroupsIdNameMapList.forEach { map ->
                map.forEach { entry ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = entry.value,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        onClick = {
                            groupName = entry.value
                            onUserGroupSelected(entry.key)
                            isExpanded = false
                        },
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }

            }
        }
    }
}