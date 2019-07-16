package com.scancriteria

import android.app.Application

class ScanApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: ScanApp
    }
}
