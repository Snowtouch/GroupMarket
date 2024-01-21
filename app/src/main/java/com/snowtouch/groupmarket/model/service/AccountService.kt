package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.User
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val currentUserId: String
    val currentUser: Flow<User>

    suspend fun createAccount(email: String, password: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun deleteAccount(password: String)
    suspend fun signOut()
}