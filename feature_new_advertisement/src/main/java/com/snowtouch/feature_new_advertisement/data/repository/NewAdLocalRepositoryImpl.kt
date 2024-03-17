package com.snowtouch.feature_new_advertisement.data.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.feature_new_advertisement.domain.repository.NewAdLocalRepository

class NewAdLocalRepositoryImpl : NewAdLocalRepository {
    override suspend fun saveAdvertisementAsDraft(advertisement : Advertisement) : Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAdvertisementDraft() : Result<Advertisement> {
        TODO("Not yet implemented")
    }
}