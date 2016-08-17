package com.greysonparrelli.mynews.utils;

import android.os.Handler;
import android.os.Looper;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
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
