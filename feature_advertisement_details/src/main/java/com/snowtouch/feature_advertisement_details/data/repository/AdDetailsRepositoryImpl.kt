package com.snowtouch.feature_advertisement_details.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response
import com.snowtouch.feature_advertisement_details.domain.repository.AdDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher

class AdDetailsRepositoryImpl(
    private val auth : FirebaseAuth,
    private val db : FirebaseDatabase,
    private val dispatcher : CoroutineDispatcher,
) : AdDetailsRepository {

    override suspend fun getAdDetails(adId : String) : Response<Advertisement> {
        TODO("Not yet implemented")
    }
}