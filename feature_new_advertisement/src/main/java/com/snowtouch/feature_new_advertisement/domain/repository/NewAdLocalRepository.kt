package com.snowtouch.feature_new_advertisement.domain.repository

import com.snowtouch.core.domain.model.Advertisement

interface NewAdLocalRepository {

    suspend fun saveAdvertisementAsDraft(advertisement : Advertisement) : Result<Boolean>

    suspend fun getAdvertisementDraft() : Result<Advertisement>
}