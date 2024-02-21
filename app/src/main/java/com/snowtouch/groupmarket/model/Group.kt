package com.snowtouch.groupmarket.model

data class Group(
    val uid: String = "",
    val ownerId: String = "",
    val ownerName: String = "",
    val members: List<String> = emptyList(),
    val name: String = "",
    val description: String = "",
    val advertisements: List<Advertisement>? = null

)
