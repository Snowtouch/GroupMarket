package com.snowtouch.auth_feature.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.snowtouch.core.domain.model.Response
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {

    val currentUser : FirebaseUser?

    suspend fun createAccountWithEmailAndPassword(
        email : String,
        password : String,
        name : String,
    ) : Response<Boolean>

    suspend fun createNewUserData(email : String, name : String) : Response<Boolean>

    suspend fun sendVerificationEmail() : Response<Boolean>

    suspend fun loginWithEmailAndPassword(email : String, password : String) : Response<Boolean>

    suspend fun deleteAccount() : Response<Boolean>

    suspend fun checkIfUserNameExists(name : String) : Response<Boolean>

    fun getAuthState(viewModelScope : CoroutineScope) : StateFlow<Boolean>
}