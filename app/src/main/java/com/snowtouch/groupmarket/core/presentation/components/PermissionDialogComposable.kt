package com.snowtouch.groupmarket.core.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.snowtouch.groupmarket.R
import com.snowtouch.groupmarket.common.ext.alertDialog

fun Modifier.alertDialog() : Modifier {
    return this.wrapContentWidth().wrapContentHeight()
}

@Composable
fun PermissionDialog(onRequestPermission: () -> Unit) {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.alertDialog(),
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(id = R.string.notification_permission_title)) } },
            text = {
                Text(
                    text = stringResource(id = R.string.notification_permission_description),
                    textAlign = TextAlign.Center) },
            shape = MaterialTheme.shapes.medium,
            confirmButton = {
                CommonButton(
                    onClick = {
                        onRequestPermission()
                        showWarningDialog = false },
                    text = stringResource(id = R.string.request_notification_permission)
                )
            },
            onDismissRequest = { }
        )
    }
}

@Composable
fun RationaleDialog() {
    var showWarningDialog by remember { mutableStateOf(true) }

    if (showWarningDialog) {
        AlertDialog(
            modifier = Modifier.alertDialog(),
            title = {
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Text(stringResource(id = R.string.notification_permission_title)) } },
            text = {
                Text(
                    text = stringResource(id = R.string.notification_permission_settings),
                    textAlign = TextAlign.Center) },
            shape = MaterialTheme.shapes.medium,
            confirmButton = {
                CommonButton(
                    onClick = { showWarningDialog = false},
                    text = stringResource(id = R.string.ok))
            },
            onDismissRequest = { showWarningDialog = false }
        )
    }
}
@Preview
@Composable
fun PermissionsDialogPreview(){
    PermissionDialog {}
}
@Preview
@Composable
fun RationaleDialogPreview(){
    RationaleDialog()
}