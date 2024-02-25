package com.snowtouch.groupmarket.model

data class User(
    val uid: String? = null,
    val email: String? = null,
    val name: String? = null,
    val groups: List<String> = emptyList(),
    val advertisements: List<String>? = null,
    val favoritesList: List<String>? = emptyList(),
    val recentlyWatched: List<String>? = null
)