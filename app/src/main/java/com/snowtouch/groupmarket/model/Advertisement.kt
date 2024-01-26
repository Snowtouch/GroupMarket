package com.snowtouch.groupmarket.model

import java.lang.invoke.TypeDescriptor

data class Advertisement(
    val id: String = "",
    val title: String = "",
    val images: List<Int> = emptyList(),
    val description: String = "",
    val price: String = "",
    val postDate: String = ""
)
