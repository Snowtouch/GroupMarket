package com.snowtouch.groupmarket.model

data class User(
    val id: String? = null,
    val email: String? = null,
    val groups: List<String>? = null,
    val advertisements: List<String>? = null,
    val favoritesList: List<String>? = emptyList(),
    val recentlyWatched: List<String>? = null
)