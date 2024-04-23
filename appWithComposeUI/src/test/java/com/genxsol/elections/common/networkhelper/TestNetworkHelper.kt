package com.genxsol.elections.common.networkhelper

import com.example.utilities.NetworkHelper

class TestNetworkHelper : NetworkHelper {
    override fun isNetworkConnected(): Boolean {
        return true
    }
}