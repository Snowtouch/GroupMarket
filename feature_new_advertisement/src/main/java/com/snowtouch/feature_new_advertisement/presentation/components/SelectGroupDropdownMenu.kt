package com.snowtouch.feature_new_advertisement.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectGroupDropdownMenu(
    modifier : Modifier = Modifier,
    userGroupsIdNamePairList : List<Pair<String, String>>,
    onUserGroupSelected : (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    var groupName by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = isExpanded,
        onExpandedChange = { newValue ->
            isExpanded = newValue
        },
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
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
            modifier = modifier
                .menuAnchor()
                .fillMaxWidth(),
            shape = MaterialTheme.shapes.medium
        )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = modifier.fillMaxWidth()
        ) {
            userGroupsIdNamePairList.forEach {
                DropdownMenuItem(
                    text = { Text(text = it.second) },
                    onClick = {
                        groupName = it.second
                        onUserGroupSelected(it.first)
                    },
                    modifier = modifier.fillMaxWidth()
                )
            }
        }
    }
}