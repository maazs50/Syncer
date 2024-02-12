package com.sms.syncer.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkCapabilities.TRANSPORT_ETHERNET
import android.net.NetworkCapabilities.TRANSPORT_WIFI
import com.sms.syncer.Utils.ConnectivityListener

class ConnectivityReceiver(val connectionListener: ConnectivityListener) : BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        if (isNetworkConnected(p0)){
            connectionListener.onInternetConnected()
        } else {
            connectionListener.onInternetDisconnected()
        }
    }

    private fun isNetworkConnected(context: Context?): Boolean {
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)?: return false
        return when{
            capabilities.hasTransport(TRANSPORT_WIFI) -> true
            capabilities.hasTransport(TRANSPORT_CELLULAR)->true
            capabilities.hasTransport(TRANSPORT_ETHERNET)->true
            else -> false
        }
    }
}