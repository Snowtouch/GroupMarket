package com.snowtouch.groupmarket.model

data class Advertisement(
    val id: String = "",
    val groupId: String = "",
    val title: String = "",
    val images: List<Int> = emptyList(),
    val description: String = "",
    val price: String = "",
    val postDate: String = ""
)
