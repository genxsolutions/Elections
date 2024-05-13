package com.genxsol.networkStatus.domain

import kotlinx.coroutines.flow.Flow

interface NetworkRepository {
    fun isNetworkConnected(): NetworkState
    fun observeNetworkConnectivity(): Flow<NetworkState>
}

sealed class NetworkState {
    object Available: NetworkState()
    object Lost: NetworkState()
}