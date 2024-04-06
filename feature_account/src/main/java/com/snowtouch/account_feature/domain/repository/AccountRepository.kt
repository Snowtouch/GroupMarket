package com.snowtouch.account_feature.domain.repository

import com.google.firebase.auth.FirebaseUser
import com.snowtouch.core.domain.model.AdvertisementPreview
import com.snowtouch.core.domain.model.Result

interface AccountRepository {

    val currentUser : FirebaseUser?
    suspend fun getUserActiveAds() : Result<List<AdvertisementPreview>>

    suspend fun getUserDraftAds() : Result<List<AdvertisementPreview>>

    suspend fun getUserFinishedAds() : Result<List<AdvertisementPreview>>

    fun signOut()
}