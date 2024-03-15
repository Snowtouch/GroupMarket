package com.snowtouch.groupmarket.core.domain.model

import java.util.Date

data class AdvertisementPreview(
    val uid : String? = null,
    val groupId : String? = null,
    val title : String? = null,
    val image : String? = null,
    val price : Double? = null,
    val postDate : Date? = null,
)