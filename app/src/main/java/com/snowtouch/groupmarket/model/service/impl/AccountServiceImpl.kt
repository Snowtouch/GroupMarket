package com.snowtouch.groupmarket.model.service.impl

import com.google.firebase.auth.FirebaseAuth
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.AccountService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
    override val currentUser: Flow<User>
        get() = callbackFlow {
            val listener = FirebaseAuth.AuthStateListener { auth ->
                this.trySend(auth.currentUser?.let { User(it.uid) } ?: User())
            }

            withContext(dispatcher) {
                auth.addAuthStateListener(listener)
            }

            awaitClose { auth.removeAuthStateListener(listener) }
        }

    override suspend fun createAccount(email: String, password: String) {
        withContext(dispatcher) {
            auth.createUserWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun authenticate(email: String, password: String) {
        withContext(dispatcher) {
            auth.signInWithEmailAndPassword(email, password).await()
        }
    }

    override suspend fun deleteAccount(password: String) {
        withContext(dispatcher) {
            auth.currentUser?.delete()?.await()
        }
    }

    override suspend fun signOut() {
        withContext(dispatcher) {
            auth.signOut()
        }
    }
}