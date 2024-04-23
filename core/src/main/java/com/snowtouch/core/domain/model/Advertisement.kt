package com.snowtouch.core.domain.model

data class Advertisement(
    val uid : String? = null,
    val ownerUid : String? = null,
    val groupId : String? = null,
    val groupName : String? = null,
    val title : String? = null,
    val images : List<String>? = null,
    val description : String? = null,
    val price : String? = null,
    val postDateTimestamp : Long? = null,
) {
    fun toAdvertisementPreview() : AdvertisementPreview {
        return AdvertisementPreview(
            uid = this.uid,
            groupId = this.groupId,
            title = this.title,
            image = this.images?.get(0),
            price = this.price,
            postDateTimestamp = this.postDateTimestamp
        )
    }
}
