package com.greysonparrelli.mynews;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

}
