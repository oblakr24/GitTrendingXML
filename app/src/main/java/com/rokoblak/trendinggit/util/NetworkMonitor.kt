package com.rokoblak.trendinggit.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


class NetworkMonitor @Inject constructor(@ApplicationContext context: Context) {

    private val _connected = MutableStateFlow(true)
    val connected = _connected.asStateFlow()

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onNetworkChanged(true)
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onNetworkChanged(false)
            }
        }
    }

    init {
        val status = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkRequest = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                .build()
            connectivityManager.registerNetworkCallback(networkRequest, networkCallback)

            connectivityManager.activeNetwork.let { network ->
                connectivityManager.getNetworkCapabilities(network)?.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_VALIDATED
                ) ?: false
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
        onNetworkChanged(status)
    }

    private fun onNetworkChanged(available: Boolean) {
        _connected.value = available
    }
}
