package com.snowtouch.groupmarket

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.snowtouch.groupmarket.common.composable.PermissionDialog
import com.snowtouch.groupmarket.common.composable.RationaleDialog
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.theme.GroupMarketTheme
import org.koin.compose.koinInject

@Composable
fun GroupMarketApp() {
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()

    GroupMarketTheme {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            RequestNotificationPermissionDialog()

        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarGlobalDelegate.snackbarHostState) {
                        Snackbar(snackbarData = it)
                    }
                }
            ) {

            }
        }
    }
}
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissionDialog() {
    val permissionState = rememberPermissionState(
        permission = Manifest.permission.POST_NOTIFICATIONS)

    if (!permissionState.status.isGranted) {
        if (permissionState.status.shouldShowRationale) RationaleDialog()
        else PermissionDialog { permissionState.launchPermissionRequest() }
    }
}