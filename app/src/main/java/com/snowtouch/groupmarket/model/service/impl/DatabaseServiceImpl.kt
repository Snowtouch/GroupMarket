package com.snowtouch.groupmarket.model.service.impl

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.snowtouch.groupmarket.model.Advertisement
import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.AccountService
import com.snowtouch.groupmarket.model.service.DatabaseService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DatabaseServiceImpl(
    auth: AccountService,
    database: FirebaseDatabase
) : DatabaseService {
    private val currentUserReference = database.getReference("users").child(auth.currentUserId)
    private val usersReference = database.getReference("users")
    private val adsReference = database.getReference("ads")

    private val _userData = MutableStateFlow<User?>(null)
    override val userData: StateFlow<User?> get() = _userData

    override suspend fun enableUserDataListener(onError: (Throwable?) -> Unit) {
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
    override suspend fun createAdvertisement(advertisement: Advertisement) {
        TODO("Not yet implemented")
    }
}