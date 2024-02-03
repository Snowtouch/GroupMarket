package com.snowtouch.groupmarket

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class GroupMarketActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            val isScreenSizeCompact = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

            GroupMarketApp(isScreenSizeCompact)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                RequestNotificationPermissionDialog()
        }
    }
}