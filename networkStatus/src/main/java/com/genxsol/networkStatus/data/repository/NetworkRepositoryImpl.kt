package com.genxsol.networkStatus.data.repository

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.genxsol.networkStatus.domain.NetworkRepository
import com.genxsol.networkStatus.domain.NetworkState
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

@Suppress("DEPRECATION")
class NetworkRepositoryImpl(private val context: Context) : NetworkRepository {
    override fun isNetworkConnected(): NetworkState {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return NetworkState.Lost
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return NetworkState.Lost
        val result = when {
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> NetworkState.Available
            else -> {
                NetworkState.Lost
            }
        }

        return result
    }

    override fun observeNetworkConnectivity(): Flow<NetworkState> {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            observeNetworkConnectivityN()
        } else {
            observeNetworkConnectivityPreN()
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun observeNetworkConnectivityN(): Flow<NetworkState> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    trySend(NetworkState.Available)
                }

                override fun onLost(network: Network) {
                    trySend(NetworkState.Lost)
                }
            }

            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose { connectivityManager.unregisterNetworkCallback(callback) }
        }.distinctUntilChanged()
    }

    private fun observeNetworkConnectivityPreN(): Flow<NetworkState> {
        return callbackFlow{
            val intentFilter = IntentFilter().apply {
                addAction(ConnectivityManager.CONNECTIVITY_ACTION)
            }

            val broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context?, intent: Intent?) {
                    if (intent?.action == ConnectivityManager.CONNECTIVITY_ACTION) {
                        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        val networkInfo = connectivityManager.activeNetworkInfo
                        val isConnected = networkInfo != null && networkInfo.isConnected

                        if (isConnected)trySend(NetworkState.Available)
                        else(trySend(NetworkState.Lost))
                    }
                }
            }

            context.registerReceiver(broadcastReceiver, intentFilter)

            awaitClose { context.unregisterReceiver(broadcastReceiver) }
        }.distinctUntilChanged()
    }
}
