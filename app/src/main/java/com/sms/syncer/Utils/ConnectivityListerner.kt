package com.sms.syncer.Utils

interface ConnectivityListener {
    fun onInternetConnected()
    fun onInternetDisconnected()
}