package com.snowtouch.groupmarket.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.firebase.auth.FirebaseUser
import com.snowtouch.groupmarket.model.Response.Success

enum class AuthState {
    SignedIn,
    SignedOut
}
object AuthDataProvider {

    var emailAndPasswordSignInResponse by mutableStateOf<FirebaseSignInResponse>(Success(null))
    var signOutResponse by mutableStateOf<SignOutResponse>(Success(false))

    var user by mutableStateOf<FirebaseUser?>(null)

    var isAuthenticated by mutableStateOf(false)

    var authState by mutableStateOf(AuthState.SignedOut)
        private set

    fun updateAuthState(user: FirebaseUser?) {
        this.user = user
        isAuthenticated = user!=null

        authState = if (isAuthenticated) {
            AuthState.SignedIn
        } else AuthState.SignedOut
    }
}