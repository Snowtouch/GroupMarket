package com.snowtouch.groupmarket.screens.new_advertisement

import android.net.Uri
import com.snowtouch.groupmarket.model.Advertisement

data class NewAdvertisementUiState(
    val title: String = "",
    val description: String = "",
    val price: String = "",
    val groupId: String = "",
    val images: List<Uri> = emptyList()
)
