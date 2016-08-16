package com.greysonparrelli.mynews;

import android.os.Handler;
import android.os.Looper;

public class UiUtil {

    public static void runOnMainThread(Runnable runnable) {
        Looper mainLooper = Looper.getMainLooper();
        if (Thread.currentThread() == mainLooper.getThread()) {
            runnable.run();
        } else {
            new Handler(mainLooper).post(runnable);
        }
    }

}
