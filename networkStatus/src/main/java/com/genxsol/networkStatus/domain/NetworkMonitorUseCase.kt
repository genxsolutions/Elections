package com.genxsol.networkStatus.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkMonitorUseCase @Inject constructor(
    private val networkRepository: NetworkRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun observeNetworkState(): Flow<NetworkState> {
        return withContext(dispatcher) {
            networkRepository.observeNetworkConnectivity()
        }
    }

    fun isNetworkConnected() = networkRepository.isNetworkConnected()
}
