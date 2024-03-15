package com.snowtouch.groupmarket

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import com.snowtouch.groupmarket.core.presentation.components.PermissionDialog
import com.snowtouch.groupmarket.core.presentation.components.RationaleDialog
import com.snowtouch.groupmarket.core.presentation.components.theme.GroupMarketTheme
import com.snowtouch.groupmarket.core.presentation.util.DisplaySize
import com.snowtouch.groupmarket.navigation.MainNavigation
import org.koin.android.ext.android.inject
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class, KoinExperimentalAPI::class)
class GroupMarketActivity : ComponentActivity() {

    private val viewModel : MainViewModel by inject()

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val navController = rememberNavController()
            val widthSizeClass = calculateWindowSizeClass(this)
            val isUserLoggedIn = !viewModel.getAuthState().collectAsState().value

            val displaySizeClass = calculateWindowSize(widthSizeClass)

            GroupMarketTheme {

                KoinAndroidContext {

                    Surface(color = MaterialTheme.colorScheme.background) {

                        MainNavigation(
                            navController = navController,
                            displaySize = displaySizeClass,
                            isLoggedIn = isUserLoggedIn
                        )
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        RequestNotificationPermissionDialog()
                }
            }
        }
    }

    private fun calculateWindowSize(windowSizeClass : WindowSizeClass) : DisplaySize {
        return when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> DisplaySize.Compact
            WindowWidthSizeClass.Expanded -> DisplaySize.Extended
            WindowWidthSizeClass.Medium -> DisplaySize.Extended
            else -> { DisplaySize.Compact }
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

