package com.snowtouch.groupmarket.model

data class User(
    val id: String = "",
    val favoritesList: List<String>? = null,
    val recentWatched: List<String>? = null
)
