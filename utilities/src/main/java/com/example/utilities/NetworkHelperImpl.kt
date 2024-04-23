package com.example.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class NetworkHelperImpl(private val context: Context) : NetworkHelper {
    override fun isNetworkConnected(): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false
        val result = when {
            networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
            else -> {
                false
            }
        }

        return result
    }
}