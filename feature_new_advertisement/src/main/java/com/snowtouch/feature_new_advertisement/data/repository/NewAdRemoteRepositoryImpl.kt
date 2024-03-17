package com.snowtouch.feature_new_advertisement.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdRemoteRepository
import kotlinx.coroutines.CoroutineDispatcher

class NewAdRemoteRepositoryImpl(
    private val auth : FirebaseAuth,
    private val db : FirebaseDatabase,
    private val store : FirebaseStorage,
    private val dispatcher : CoroutineDispatcher
) : NewAdRemoteRepository {

    override suspend fun postNewAdvertisement(advertisement : Advertisement) : Response<Boolean> {
        TODO("Not yet implemented")
    }
}