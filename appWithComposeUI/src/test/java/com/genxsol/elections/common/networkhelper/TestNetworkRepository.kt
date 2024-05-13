package com.genxsol.elections.common.networkhelper

import com.genxsol.networkStatus.domain.NetworkRepository
import com.genxsol.networkStatus.domain.NetworkState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestNetworkRepository : NetworkRepository {
    override fun isNetworkConnected(): NetworkState {
        return NetworkState.Available
    }

    override fun observeNetworkConnectivity(): Flow<NetworkState> {
       return flowOf(NetworkState.Available)
    }
}