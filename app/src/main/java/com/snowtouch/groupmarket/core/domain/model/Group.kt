package com.snowtouch.groupmarket.core.domain.model

data class Group(
    val uid: String? = null,
    val ownerId: String? = null,
    val ownerName: String? = null,
    val members: List<String>? = null,
    val name: String? = null,
    val description: String? = null,
    val advertisements: List<Advertisement>? = null
)
