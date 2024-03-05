package com.snowtouch.groupmarket.groups.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsTopAppBar(
    modifier : Modifier = Modifier,
    onAddGroupClick: () -> Unit
) {
    TopAppBar(
        title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text(text = "Your groups") } },
        actions = {
            IconButton(onClick = onAddGroupClick) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Create new group") }
        }
    )
}