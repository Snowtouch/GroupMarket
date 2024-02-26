package com.snowtouch.groupmarket.model.service.impl

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.snowtouch.groupmarket.model.AuthDataProvider
import com.snowtouch.groupmarket.model.AuthStateResponse
import com.snowtouch.groupmarket.model.SignOutResponse
import com.snowtouch.groupmarket.model.FirebaseSignInResponse.Success
import com.snowtouch.groupmarket.model.FirebaseSignInResponse.Failure
import com.snowtouch.groupmarket.model.FirebaseSignInResponse
import com.snowtouch.groupmarket.model.Response
import com.snowtouch.groupmarket.model.service.AccountService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AccountServiceImpl(
    private val auth: FirebaseAuth,
    private val dispatcher: CoroutineDispatcher
) : AccountService {

    override val userLogged: Boolean
        get() = auth.currentUser != null
    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override fun getAuthState(viewModelScope: CoroutineScope): AuthStateResponse = callbackFlow {
        val authStateListener = withContext(dispatcher) {
            AuthStateListener { auth ->
                trySend(auth.currentUser)
                Log.i(TAG, "User: ${auth.currentUser?.uid ?: "Not authenticated"}")
            }
        }
        auth.addAuthStateListener(authStateListener)

        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser)

    override suspend fun createAccount(email: String, password: String) {
        withContext(dispatcher) {
            try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                AuthDataProvider.emailAndPasswordSignInResponse = Success(result)
            } catch (e: Exception) {
                AuthDataProvider.emailAndPasswordSignInResponse = Failure(e)
            }
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        withContext(dispatcher) {
            try {
                val result = auth.signInWithEmailAndPassword(email, password).await()
                AuthDataProvider.emailAndPasswordSignInResponse = Success(result)
            } catch (e: Exception) {
                AuthDataProvider.emailAndPasswordSignInResponse = Failure(e)
            }
        }
    }

    override suspend fun deleteAccount(password: String) {
        withContext(dispatcher) {
            try {
                auth.currentUser?.delete()?.await()
                AuthDataProvider.signOutResponse = Success(true)
            } catch (e: Exception) {
                AuthDataProvider.signOutResponse = Failure(e)
            }
        }
    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            try {
                auth.signOut()
                AuthDataProvider.signOutResponse = Success(true)
            } catch (e: Exception) {
                AuthDataProvider.signOutResponse = Failure(e)
            }
        }
    }
}
