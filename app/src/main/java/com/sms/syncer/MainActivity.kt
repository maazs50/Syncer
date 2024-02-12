package com.sms.syncer

import android.content.IntentFilter
import android.os.Build.VERSION_CODES.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.sms.syncer.Utils.ConnectivityListener
import com.sms.syncer.receiver.ConnectivityReceiver

class MainActivity : AppCompatActivity(),ConnectivityListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        registerReceiver(ConnectivityReceiver(this), IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"))
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(ConnectivityReceiver(this))
    }
    override fun onInternetConnected() {
        showSnackbar("Connected!")
    }

    override fun onInternetDisconnected() {
        showSnackbar("No Internet!")
    }

    private fun showSnackbar(message: String) {
        val rootView = findViewById<ConstraintLayout>(R.id.main_activity)  // Replace with your root layout ID
        Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
    }
}