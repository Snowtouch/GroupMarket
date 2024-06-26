package com.snowtouch.core.domain.model

data class Group(
    val uid: String? = null,
    val ownerId: String? = null,
    val ownerName: String? = null,
    val members: List<String>? = null,
    val membersCount: Int? = null,
    val name: String? = null,
    val description: String? = null,
    val advertisements: List<Advertisement>? = null,
    val advertisementsCount: Int? = null
)

fun Group.toGroupPreview() : GroupPreview {
    return GroupPreview(
        uid = uid,
        ownerId = ownerId,
        ownerName = ownerName,
        membersCount = membersCount,
        name = name,
        description = description,
        advertisementsCount = advertisementsCount
    )
}