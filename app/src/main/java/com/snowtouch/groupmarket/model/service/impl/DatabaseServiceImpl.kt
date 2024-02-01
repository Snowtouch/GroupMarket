package com.snowtouch.groupmarket.model.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.snapshots
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.model.service.DatabaseService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DatabaseServiceImpl(
    private val auth: AccountService,
    private val database: FirebaseDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : DatabaseService {

    private val currentUserReference = database.getReference("users").child(auth.currentUserId)
    private val usersReference = database.getReference("users")
    private val adsReference = database.getReference("ads")

    private val _userData = MutableStateFlow<User?>(null)
    override val userData: StateFlow<User?> get() = _userData

    override suspend fun enableUserDataListener(onError: (Throwable?) -> Unit) {
        withContext(ioDispatcher) {
            currentUserReference
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val newData = snapshot.getValue<User?>()
                        _userData.value = newData
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onError(Exception(error.message))
                    }
                })
        }
    }
    override suspend fun createAdvertisement(advertisement: Advertisement) {
        withContext(ioDispatcher){
            val key = adsReference.push().key
            val advertisementValues = advertisement.toMap()

            val childUpdates = hashMapOf<String, Any>(
                "/ads/$key" to advertisementValues,
                "/users/${auth.currentUserId}/advertisements/$key" to advertisementValues,
                "/groups/${advertisement.groupId}/$key" to advertisementValues
            )
            database.reference.updateChildren(childUpdates).await()
        }
    }

    override suspend fun getLatestAdvertisementsList(): List<Advertisement> = withContext(ioDispatcher) {
        try {
            val newestAds = adsReference
                .limitToFirst(10)
                .get()
                .await()

            val advertisementsList = mutableListOf<Advertisement>()

            for (adSnapshot in newestAds.children) {
                val advertisement = adSnapshot.getValue(Advertisement::class.java)
                advertisement?.let {
                    advertisementsList.add(it)
                }
            }

            return@withContext advertisementsList
        } catch (e: Exception) {
            return@withContext emptyList()
        }
    }

    override suspend fun getUserFavoriteAdvertisementsList(): List<Advertisement> = withContext(ioDispatcher) {
        val favoritesList = _userData.value?.favoritesList.orEmpty()

        if (favoritesList.isNotEmpty()) {
            val favoriteAdsSnapshot = adsReference
                .orderByKey()
                .startAt(favoritesList.first())
                .endAt(favoritesList.last())
                .get()
                .await()

            return@withContext favoriteAdsSnapshot.children.mapNotNull { adSnapshot ->
                adSnapshot.getValue(Advertisement::class.java)
            }
        }

        return@withContext emptyList()
    }
}