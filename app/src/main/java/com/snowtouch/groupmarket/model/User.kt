package com.snowtouch.groupmarket.model

data class User(
    val id: String? = null,
    val groups: List<String>? = null,
    val advertisements: List<String>? = null,
    val favoritesList: List<String>? = null,
    val recentlyWatched: List<String>? = null
)