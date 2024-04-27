package com.snowtouch.core.domain.model

data class GroupPreview(
    val uid : String? = null,
    val ownerId: String? = null,
    val ownerName: String? = null,
    val membersCount: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val advertisementsCount: Int? = null
)
