package com.greysonparrelli.mynews;

import com.greysonparrelli.mynews.models.FeedItem;
import com.greysonparrelli.mynews.models.Feed;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NetworkUtil {

    public static void getFeed(String url, final FeedCallback callback) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
                final Feed feed = parseFeed(response.body().charStream());
                UiUtil.runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResponse(feed);
                    }
                });
            }
        });
    }

    private static Feed parseFeed(Reader in) throws IOException {
        Feed feed = new Feed();

        try {
            XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
            parser.setInput(in);

            FeedItem currentFeedItem = null;
            String text = null;
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        if (parser.getName().equalsIgnoreCase("entry") || parser.getName().equalsIgnoreCase("item")) {
                            currentFeedItem = new FeedItem();
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        break;
                    case XmlPullParser.END_TAG:
                        String name = parser.getName();
                        if (currentFeedItem == null) {
                            if (name.equalsIgnoreCase("title")) {
                                feed.setTitle(text);
                            } else if (name.equalsIgnoreCase("icon")) {
                                feed.setIconUrl(text);
                            } else if (name.equalsIgnoreCase("link")) {
                                feed.setLink(text);
                            }
                        } else {
                            if (name.equalsIgnoreCase("entry") || name.equalsIgnoreCase("item")) {
                                feed.addEntry(currentFeedItem);
                                currentFeedItem = null;
                            } else if (name.equalsIgnoreCase("title")) {
                                currentFeedItem.setTitle(text);
                            } else if (name.equalsIgnoreCase("content") || name.equalsIgnoreCase("description")) {
                                currentFeedItem.setContent(text);
                            } else if (name.equalsIgnoreCase("link")) {
                                currentFeedItem.setLink(text);
                            }
                        }
                        break;
                }
                parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        return feed;
    }

    public interface FeedCallback {
        void onFailure(Call call, IOException e);
        void onResponse(Feed body);
    }
}
