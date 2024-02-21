package com.snowtouch.groupmarket

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.snowtouch.groupmarket.model.AuthDataProvider
import org.koin.android.ext.android.inject

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class GroupMarketActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            val windowSizeClass = calculateWindowSizeClass(this)
            val isScreenSizeCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

            val currentUser = authViewModel.currentUser.collectAsStateWithLifecycle().value

            AuthDataProvider.updateAuthState(currentUser)

            GroupMarketApp(
                isScreenSizeCompact =  isScreenSizeCompact,
                isLoggedIn = AuthDataProvider.isAuthenticated
            )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
               RequestNotificationPermissionDialog()
        }
    }

    override fun onStart() {
        super.onStart()

        if (AuthDataProvider.isAuthenticated) {
            authViewModel.enableUserDataListener()
        }
    }

    override fun onStop() {
        super.onStop()

        if (AuthDataProvider.isAuthenticated) {
            authViewModel.disableUserDataListener()
        }
    }
}