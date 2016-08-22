package com.greysonparrelli.mynews.data;

import com.raizlabs.android.dbflow.annotation.Database;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
@Database(name = AppDatabase.NAME, version = AppDatabase.VERSION)
public class AppDatabase {
    public static final String NAME = "my_news";
    public static final int VERSION = 1;
}
