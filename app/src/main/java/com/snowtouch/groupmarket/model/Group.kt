package com.snowtouch.groupmarket.model

data class Group(
    val id: String = "",
    val ownerId: String = "",
    val name: String = "",
    val description: String = "",
    val advertisements: List<Advertisement>? = null,
    val members: List<String>
)
