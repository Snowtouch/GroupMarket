package com.snowtouch.groupmarket.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase
import com.snowtouch.groupmarket.auth.domain.repository.AuthRepository
import com.snowtouch.groupmarket.core.domain.model.Response
import com.snowtouch.groupmarket.core.domain.model.Response.Failure
import com.snowtouch.groupmarket.core.domain.model.Response.Success
import com.snowtouch.groupmarket.core.domain.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val auth: FirebaseAuth,
    db: FirebaseDatabase,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

    private val usersReference = db.getReference("users")
    private val userNamesReference = db.getReference("user_names")

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String,
        name : String
    ): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()

                val currentUser = auth.currentUser
                currentUser.let { user ->
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)?.await()
                }
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun createNewUserData(email: String, name: String): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val newUser = User(
                uid = auth.currentUser?.uid,
                email = email,
                name = name,
                groups = null,
                advertisements = null,
                favoritesList = null,
                recentlyWatched = null
            )
                usersReference
                    .child(currentUser?.uid!!)
                    .setValue(newUser)
                    .await()

                userNamesReference
                    .push()
                    .setValue(name)
                    .await()

                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun sendVerificationEmail(): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.currentUser?.sendEmailVerification()?.await()
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun loginWithEmailAndPassword(
        email: String,
        password: String
    ): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun deleteAccount(): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.currentUser?.delete()?.await()
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun checkIfUserNameExists(name: String): Response<Boolean> {
        return withContext(dispatcher) {
            try {
                val isName = userNamesReference
                    .equalTo(name)
                    .get()
                    .await()
                if (isName.exists()) {
                    Success(true)
                } else {
                    Failure(Exception("User name is taken"))
                }
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override fun getAuthState(viewModelScope: CoroutineScope) = callbackFlow {
        val authStateListener = AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

}
