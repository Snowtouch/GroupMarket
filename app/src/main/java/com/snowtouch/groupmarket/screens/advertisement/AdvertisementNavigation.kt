package com.snowtouch.groupmarket.screens.advertisement

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import org.koin.androidx.compose.koinViewModel

private const val advertisementIdArg = "advertisementId"

fun NavGraphBuilder.advertisementDetailScreen(onNavigateBack: () -> Unit) {
    composable("advertisement/${advertisementIdArg}") {
        val viewModel: AdvertisementScreenViewModel = koinViewModel()
        AdvertisementDetailScreen(
            viewModel,
            onNavigateBack
        )
    }
}
fun NavController.navigateToAdvertisement(advertisementId: String) {
    this.navigate("advertisement/$advertisementId")
}
internal class AdvertisementArgs(advertisementId: String) {
    constructor(savedStateHandle: SavedStateHandle) :
            this(checkNotNull(savedStateHandle[advertisementIdArg]) as String)
}