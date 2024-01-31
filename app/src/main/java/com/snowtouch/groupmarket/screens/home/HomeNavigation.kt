package com.snowtouch.groupmarket.screens.home

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable


const val homeRoute = "home"

fun NavGraphBuilder.homeScreen(
    onNavigateToAdDetails: () -> Unit
) {
    composable(homeRoute) {
        HomeScreen(navigateToAdDetails = onNavigateToAdDetails)
    }
}