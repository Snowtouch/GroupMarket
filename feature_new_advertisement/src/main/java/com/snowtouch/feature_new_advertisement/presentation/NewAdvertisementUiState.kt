package com.snowtouch.feature_new_advertisement.presentation

import android.net.Uri

data class NewAdvertisementUiState(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val groupId: String = "",
    val images: List<Uri> = emptyList()
)
