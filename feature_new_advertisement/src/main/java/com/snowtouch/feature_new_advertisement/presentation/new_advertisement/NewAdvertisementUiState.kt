package com.snowtouch.feature_new_advertisement.presentation.new_advertisement

import android.net.Uri

data class NewAdvertisementUiState(
    val newAdId : String = "",
    val title : String = "",
    val description : String = "",
    val price : String = "",
    val groupId : String = "",
    val images : List<Uri> = emptyList(),
    val imageIndex : Int = 0,
)
