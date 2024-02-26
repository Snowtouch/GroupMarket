package com.snowtouch.groupmarket.model

import android.net.Uri
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Advertisement(
    val uid: String = "",
    val ownerUid: String = "",
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
            "ownerUid" to ownerUid,
            "groupId" to groupId,
            "title" to title,
            "images" to images,
            "description" to description,
            "price" to price,
            "postDate" to postDate
        )
    }
}
