package com.snowtouch.groupmarket.screens.home

import com.snowtouch.groupmarket.model.User
import com.snowtouch.groupmarket.model.service.DatabaseService
import com.snowtouch.groupmarket.screens.GroupMarketViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.context.GlobalContext.get

class HomeScreenViewModel(
    databaseService: DatabaseService
) : GroupMarketViewModel() {
    private val _userData: StateFlow<User?> = databaseService.userData
    val userData: StateFlow<User?> = _userData
}