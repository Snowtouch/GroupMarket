package com.snowtouch.groupmarket.model.service

import com.snowtouch.groupmarket.model.AuthStateResponse
import com.snowtouch.groupmarket.model.FirebaseSignInResponse
import com.snowtouch.groupmarket.model.SignOutResponse
import kotlinx.coroutines.CoroutineScope

interface AccountService {
    val userLogged: Boolean
    val currentUserId: String

    fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse
    suspend fun createAccount(email: String, password: String)
    suspend fun authenticate(email: String, password: String)
    suspend fun deleteAccount(password: String)
    suspend fun signOut()
}