package com.snowtouch.auth_feature.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.snowtouch.core.domain.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val currentUser : FirebaseUser?

    suspend fun createAccountWithEmailAndPassword(
        email : String,
        password : String,
        name : String,
    ) : Result<Boolean>

    suspend fun createNewUserData(email : String, name : String) : Result<Boolean>

    suspend fun sendVerificationEmail() : Result<Boolean>

    suspend fun loginWithEmailAndPassword(email : String, password : String) : Result<Boolean>

    suspend fun deleteAccount() : Result<Boolean>

    suspend fun checkIfUserNameExists(name : String) : Result<Boolean>

    fun getAuthState(viewModelScope : CoroutineScope) : StateFlow<Boolean>
}