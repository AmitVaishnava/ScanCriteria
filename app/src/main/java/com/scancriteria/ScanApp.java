package com.scancriteria;

import android.app.Application;

public class ScanApp extends Application {

    public static ScanApp scanApp;

    public static ScanApp getInstance() {
        return scanApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        scanApp = this;
    }
}
