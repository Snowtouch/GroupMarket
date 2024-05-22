package com.snowtouch.core.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Result
import com.snowtouch.core.domain.repository.CoreRepository
import com.snowtouch.core.domain.repository.DatabaseReferenceManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.tasks.await

class CoreRepositoryImpl(
    private val dbReference : DatabaseReferenceManager,
    private val auth : FirebaseAuth,
) : CoreRepository {
    override fun getAuthState(viewModelScope : CoroutineScope) = callbackFlow {
        val authStateListener = FirebaseAuth.AuthStateListener { auth ->
            trySend(auth.currentUser == null)
        }
        auth.addAuthStateListener(authStateListener)
        awaitClose {
            auth.removeAuthStateListener(authStateListener)
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), auth.currentUser == null)

    override fun getUserFavoriteAdIds(viewModelScope : CoroutineScope) = callbackFlow {
        try {
            val initialData = dbReference
                .currentUserFavoriteAdsIds
                .get()
                .await()
                .children.mapNotNull { it.getValue<String>() }

            if (initialData.isNotEmpty()) {
                this@callbackFlow.trySend(Result.Success(initialData))
            } else {
                this@callbackFlow.trySend(Result.Success(emptyList()))
            }
        } catch (e : Exception) {
            this@callbackFlow.trySend(Result.Failure(e))
        }

        val favoriteAdsListener = object : ValueEventListener {
            override fun onDataChange(snapshot : DataSnapshot) {
                val adIds = snapshot.children.mapNotNull { it.getValue<String>() }
                this@callbackFlow.trySend(Result.Success(adIds))
            }

            override fun onCancelled(error : DatabaseError) {
                this@callbackFlow.trySend(Result.Failure(error.toException()))
            }
        }
        dbReference.currentUserFavoriteAdsIds.addValueEventListener(favoriteAdsListener)

        awaitClose {
            dbReference.currentUserFavoriteAdsIds.removeEventListener(favoriteAdsListener)
        }
    }

    override suspend fun toggleFavoriteAd(adId : String) : Result<Boolean> {
        return try {
            val favAdsIds = dbReference
                .currentUserFavoriteAdsIds
                .get()
                .await()
                .children
                .mapNotNull {
                    it.getValue<String>()
                }
            Log.d("FavoriteToggle", "FavAdsIds: $favAdsIds")

            if (favAdsIds.contains(adId)) {
                val idRef = dbReference.currentUserFavoriteAdsIds
                    .orderByValue()
                    .equalTo(adId)
                    .get()
                    .await()
                    .children
                    .firstOrNull()
                    ?.ref
                idRef?.removeValue()

                Result.Success(false)
            } else {
                dbReference.currentUserFavoriteAdsIds
                    .push()
                    .setValue(adId)
                    .await()
                Result.Success(true)
            }
        } catch (e : Exception) {
            Result.Failure(e)
        }
    }

    override suspend fun getAdDetails(adId : String) : Result<Advertisement> {
        return try {
            val adSnapshot = dbReference.advertisements.child(adId)
                .get().await()
            val ad = adSnapshot.getValue(Advertisement::class.java)
            if (ad == null) {
                Result.Failure(Exception("No such advertisement"))
            } else {
                Result.Success(ad)
            }
        } catch (e : Exception) {
            Result.Failure(e)
        }
    }
}