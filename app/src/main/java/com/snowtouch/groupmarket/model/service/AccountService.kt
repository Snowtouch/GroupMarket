package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.AuthStateResponse
import com.snowtouch.groupmarket.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AccountService {
    val userLogged: Boolean
    val currentUserId: String

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun createAccount(email: String, password: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun deleteAccount(password: String)
    suspend fun signOut()
}