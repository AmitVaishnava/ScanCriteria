package com.scancriteria.network

import android.content.Context
import android.net.ConnectivityManager
import com.scancriteria.ScanApp

object NetworkUtils {

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager =
                ScanApp.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
}
