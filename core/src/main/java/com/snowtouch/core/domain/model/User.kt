package com.snowtouch.core.domain.model

data class User(
    val uid: String? = null,
    val email: String? = null,
    val name: String? = null,
    val groups: List<String>? = null,
    val advertisements: List<String>? = null,
    val favoritesList: List<String>? = null,
    val recentlyWatched: List<String>? = null
)