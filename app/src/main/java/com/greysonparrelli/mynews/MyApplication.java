package com.greysonparrelli.mynews;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }

}
