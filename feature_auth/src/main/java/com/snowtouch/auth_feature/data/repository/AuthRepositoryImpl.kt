package com.snowtouch.auth_feature.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.snowtouch.auth_feature.domain.repository.AuthRepository
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.model.Result.Failure
import com.snowtouch.core.domain.model.Result.Success
import com.snowtouch.core.domain.model.User
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
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
    private val dbReferences : DatabaseReferenceManager,
    private val dispatcher: CoroutineDispatcher
) : AuthRepository {

    override val currentUser: FirebaseUser?
        get() = auth.currentUser

    override suspend fun createAccountWithEmailAndPassword(
        email: String,
        password: String,
        name : String
    ): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.createUserWithEmailAndPassword(email, password).await()
                createNewUserData(email, name)
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

    override suspend fun createNewUserData(email: String, name: String): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                val newUser = User(
                    uid = auth.currentUser?.uid,
                    email = email,
                    name = name,
                )
                dbReferences.users
                    .child(currentUser?.uid!!)
                    .setValue(newUser)
                    .await()

                dbReferences.userNamesList
                    .push()
                    .setValue(name)
                    .await()

                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun sendVerificationEmail(): Result<Boolean> {
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
    ): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.signInWithEmailAndPassword(email, password).await()
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun deleteAccount(): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                auth.currentUser?.delete()?.await()
                Success(true)
            } catch (e: Exception) {
                Failure(e)
            }
        }
    }

    override suspend fun checkIfUserNameExists(name: String): Result<Boolean> {
        return withContext(dispatcher) {
            try {
                val isName = dbReferences.userNamesList
                    .equalTo(name)
                    .get()
                    .await()
                if (isName.exists()) {
                    Failure(Exception("User name is taken"))
                } else {
                    Success(true)
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
