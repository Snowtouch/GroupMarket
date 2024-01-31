package com.snowtouch.groupmarket.model.service.impl

import com.snowtouch.groupmarket.model.service.StorageService
import kotlinx.coroutines.CoroutineDispatcher

class StorageServiceImpl(private val ioDispatcher: CoroutineDispatcher) : StorageService