package com.engineering.baseproj.utility

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.hasNetwork(): Boolean? {
    var isConnected: Boolean? = false // Initial Value
    val connectivityManager =
        getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected)
        isConnected = true
    return isConnected
}

fun AppCompatActivity.showLongToast(msg: String) {
    if (msg.isNotEmpty()) {
        Toast.makeText(
            this, msg,
            Toast.LENGTH_LONG
        ).show()
    }

}

fun AppCompatActivity.showShortToast(msg: String) {
    if (msg.isNotEmpty()) {
        Toast.makeText(
            this, msg,
            Toast.LENGTH_SHORT
        ).show()
    }
}