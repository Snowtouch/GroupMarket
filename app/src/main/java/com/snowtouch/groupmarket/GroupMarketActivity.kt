package com.snowtouch.groupmarket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.snowtouch.core.domain.network_utils.ConnectionState
import com.snowtouch.core.presentation.util.theme.GroupMarketTheme
import com.snowtouch.core.presentation.util.DisplaySize
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
            val isUserLoggedIn = viewModel.getAuthState().collectAsStateWithLifecycle()
            val networkStatus =
                viewModel.getNetworkState(applicationContext).collectAsStateWithLifecycle()

            val displaySizeClass = calculateWindowSize(widthSizeClass)

            GroupMarketTheme {
                KoinAndroidContext {
                    Surface(color = MaterialTheme.colorScheme.background) {
                        Column {
                            ConnectivityStatusBar(connectionState = networkStatus.value)
                            MainNavigation(
                                navController = navController,
                                displaySize = displaySizeClass,
                                isLoggedIn = !isUserLoggedIn.value
                            )
                        }
                    }
                }
            }
        }
    }

    private fun calculateWindowSize(windowSizeClass : WindowSizeClass) : DisplaySize {
        return when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> DisplaySize.Compact
            WindowWidthSizeClass.Medium -> DisplaySize.Medium
            WindowWidthSizeClass.Expanded -> DisplaySize.Extended
            else -> DisplaySize.Compact
        }
    }
}

@Composable
fun ConnectivityStatusBar(
    connectionState : ConnectionState,
    modifier : Modifier = Modifier,
) {
    AnimatedVisibility(visible = connectionState == ConnectionState.Unavailable) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    if (connectionState == ConnectionState.Unavailable) Color.Red
                    else Color.Green.copy(alpha = 0.6f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Network $connectionState",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun ConnectivityStatusBarPreview() {
    ConnectivityStatusBar(connectionState = ConnectionState.Unavailable)
}