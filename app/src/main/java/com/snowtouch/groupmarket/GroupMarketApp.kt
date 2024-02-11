package com.snowtouch.groupmarket

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.snowtouch.groupmarket.common.composable.BigScreenNavBar
import com.snowtouch.groupmarket.common.composable.CompactScreenNavBar
import com.snowtouch.groupmarket.common.composable.PermissionDialog
import com.snowtouch.groupmarket.common.composable.RationaleDialog
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.koin_modules.firebaseModule
import com.snowtouch.groupmarket.koin_modules.serviceModule
import com.snowtouch.groupmarket.koin_modules.snackbarModule
import com.snowtouch.groupmarket.koin_modules.viewModelModule
import com.snowtouch.groupmarket.theme.GroupMarketTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GroupMarketApp(
    isScreenSizeCompact: Boolean,
    isLoggedIn: Boolean
) {
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()
    val navController = rememberNavController()

    GroupMarketTheme {
        KoinAndroidContext {
            Surface(color = MaterialTheme.colorScheme.background) {
                Scaffold(
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarGlobalDelegate.snackbarHostState) {
                            Snackbar(snackbarData = it) } },
                    bottomBar = {
                        if (isScreenSizeCompact) {
                            CompactScreenNavBar(navController)
                        } else {
                            BigScreenNavBar(navController)
                        }
                    }
                ) { paddingValues ->
                    MainNavigation(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        isLoggedIn = isLoggedIn
                    )
                }
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

@Preview
@Composable
fun GroupMarketPreview(){
    KoinApplication(application = {
        modules(snackbarModule, firebaseModule, serviceModule, viewModelModule) }
    ) {
        val navController = rememberNavController()
        MainNavigation(
            navController = navController,
            isLoggedIn = false)
    }
}