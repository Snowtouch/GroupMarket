package com.snowtouch.feature_advertisement_details.domain.repository

import com.snowtouch.core.domain.model.Advertisement
import com.snowtouch.core.domain.model.Response

interface AdDetailsRepository {

    suspend fun getAdDetails(adId: String) : Response<Advertisement>
}