package com.snowtouch.groupmarket.model

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Advertisement(
    val uid: String = "",
    val groupId: String = "",
    val title: String = "",
    val images: List<String> = emptyList(),
    val description: String = "",
    val price: String = "",
    val postDate: String = ""
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "groupId" to groupId,
            "title" to title,
            "images" to images,
            "description" to description,
            "price" to price,
            "postDate" to postDate
        )
    }
}
