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
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.snowtouch.groupmarket.common.composable.BigScreenNavBar
import com.snowtouch.groupmarket.common.composable.CompactScreenNavBar
import com.snowtouch.groupmarket.common.composable.PermissionDialog
import com.snowtouch.groupmarket.common.composable.RationaleDialog
import com.snowtouch.groupmarket.common.snackbar.SnackbarGlobalDelegate
import com.snowtouch.groupmarket.koin_modules.snackbarModule
import com.snowtouch.groupmarket.screens.home.HomeScreen
import com.snowtouch.groupmarket.theme.GroupMarketTheme
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
fun GroupMarketApp(isScreenSizeCompact: Boolean) {
    val snackbarGlobalDelegate = koinInject<SnackbarGlobalDelegate>()
    val navController = rememberNavController()
    GroupMarketTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            Scaffold(
                snackbarHost = {
                    SnackbarHost(hostState = snackbarGlobalDelegate.snackbarHostState) {
                        Snackbar(snackbarData = it) }
                },
                bottomBar = {
                    if (isScreenSizeCompact) {
                        CompactScreenNavBar(navController) }
                    else  {
                        BigScreenNavBar(navController) }
                }
            ) { paddingValues ->

                NavHost(
                    navController = navController,
                    startDestination = HOME_SCREEN,
                    modifier = Modifier.padding(paddingValues),
                    builder = {
                        groupMarketGraph(navController)
                    }
                )
            }
        }
    }
}

fun NavGraphBuilder.groupMarketGraph(navController: NavHostController) {
    composable(HOME_SCREEN) {
        HomeScreen()
    }
    composable(
        route = "$AD_DETAIL_SCREEN$AD_ID_ARG",
        arguments = listOf(navArgument(AD_ID) {
            nullable = true
            defaultValue = null
        })
    ) {

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
        modules(snackbarModule) }
    ) {
        GroupMarketApp(isScreenSizeCompact = true)
    }
}