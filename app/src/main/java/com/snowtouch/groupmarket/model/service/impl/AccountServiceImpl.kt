package com.snowtouch.groupmarket.model.service.impl

import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.AccountService
import kotlinx.coroutines.flow.Flow

class AccountServiceImpl : AccountService {
    override val currentUserId: String
        get() = TODO("Not yet implemented")
    override val currentUser: Flow<User>
        get() = TODO("Not yet implemented")

    override suspend fun createAccount(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun authenticate(email: String, password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAccount(password: String) {
        TODO("Not yet implemented")
    }

    override suspend fun signOut() {
        TODO("Not yet implemented")
    }
}