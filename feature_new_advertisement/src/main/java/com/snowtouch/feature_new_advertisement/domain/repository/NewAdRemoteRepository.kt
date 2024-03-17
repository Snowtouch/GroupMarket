package com.snowtouch.feature_new_advertisement.domain.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response

interface NewAdRemoteRepository {

    suspend fun postNewAdvertisement(advertisement : Advertisement) : Response<Boolean>
}