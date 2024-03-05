package com.snowtouch.groupmarket.navigation

sealed class MainScreenRoutes(val route: String) {
    data object Home: MainScreenRoutes("home")
    data object NewAd: MainScreenRoutes("new_ad")
    data object Messages: MainScreenRoutes("messages")
    data object Account: MainScreenRoutes("account")
}