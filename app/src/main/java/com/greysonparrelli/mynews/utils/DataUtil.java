package com.greysonparrelli.mynews.utils;

import android.util.Log;

import com.greysonparrelli.mynews.models.Feed;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class DataUtil {

    public static final String TAG = "DataUtil";

    public static void updateFeedDatabase() {
        NetworkUtil.getFeedDatabase(new NetworkUtil.FeedsCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to update feed database.", e);
            }

            @Override
            public void onResponse(List<Feed> feeds) {
                for (Feed feed : feeds) {
                    feed.save();
                }
            }
        });
    }

    public static void updateFeedContents(Feed feed) {
        NetworkUtil.getFeed(feed.url, new NetworkUtil.FeedCallback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to update feed contents.", e);
            }

            @Override
            public void onResponse(Feed feed) {
                feed.save();
            }
        });
    }
}
