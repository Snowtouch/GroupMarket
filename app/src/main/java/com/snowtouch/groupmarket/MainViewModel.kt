package com.snowtouch.groupmarket

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.snowtouch.core.domain.network_utils.ConnectionState
import com.snowtouch.core.domain.network_utils.NetworkStateProvider
import com.snowtouch.core.domain.use_case.GetAuthStateUseCase
import com.snowtouch.core.presentation.GroupMarketViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel(
    private val getAuthStateUseCase : GetAuthStateUseCase,
) : GroupMarketViewModel() {

    init {
        getAuthState()
    }

    fun getAuthState() = getAuthStateUseCase.invoke(viewModelScope)

    fun getNetworkState(context : Context) = NetworkStateProvider(context = context)
        .currentConnectivityState
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectionState.Unavailable)

}