package com.snowtouch.groupmarket.core.domain.model

import java.util.Date

data class Advertisement(
    val uid: String? = null,
    val ownerUid: String? = null,
    val groupId: String? = null,
    val title: String? = null,
    val images: List<String>? = null,
    val description: String? = null,
    val price: Double? = null,
    val postDate: Date? = null
)
