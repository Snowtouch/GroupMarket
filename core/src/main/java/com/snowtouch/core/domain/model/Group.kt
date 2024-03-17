package com.snowtouch.core.domain.model

data class Group(
    val uid: String? = null,
    val ownerId: String? = null,
    val ownerName: String? = null,
    val members: List<String>? = null,
    val membersCount: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val advertisements: List<com.snowtouch.core.domain.model.Advertisement>? = null,
    val advertisementsCount: Int? = null
)
