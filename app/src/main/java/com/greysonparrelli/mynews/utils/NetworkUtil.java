package com.greysonparrelli.mynews.utils;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.greysonparrelli.mynews.models.FeedItem;
import com.greysonparrelli.mynews.models.Feed;
import com.greysonparrelli.mynews.models.FeedItemImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class NetworkUtil {

    private static OkHttpClient sClient;
    static {
        sClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();
    }

    public static void getFeedDatabase(final FeedsCallback callback) {
        Request request = new Request.Builder()
                .url("https://greysonp.github.io/rss-feeds/feeds.json")
                .build();
        sClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(call, e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String body = response.body().string();
                List<Feed> feeds = new ArrayList<>();
                try {
                    JSONObject root = new JSONObject(body);
                    JSONArray jsonFeeds = root.getJSONArray("feeds");
                    for (int i = 0; i < jsonFeeds.length(); i++) {
                        JSONObject feed = jsonFeeds.getJSONObject(i);
                        feeds.add(parseFeed(feed));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                callback.onResponse(feeds);
            }
        });
    }

    public static void getFeed(String url, final FeedCallback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        sClient.newCall(request).enqueue(new Callback() {
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
            List<FeedItemImage> images = new ArrayList<>();
            String name = null;
            String text = null;
            String link = null;
            String feedUrl = null;
            while (parser.getEventType() != XmlPullParser.END_DOCUMENT) {
                switch (parser.getEventType()) {
                    case XmlPullParser.START_TAG:
                        name = parser.getName();
                        if (name.equalsIgnoreCase("entry") || parser.getName().equalsIgnoreCase("item")) {
                            currentFeedItem = new FeedItem();
                        } else if (name.equalsIgnoreCase("link")) {
                            link = parser.getAttributeValue(null, "href");
                        } else if (name.equalsIgnoreCase("media:thumbnail")) {
                            FeedItemImage image = new FeedItemImage();
                            image.url = parser.getAttributeValue(null, "url");
                            images.add(image);
                        } else if (name.equalsIgnoreCase("atom:link")) {
                            feedUrl = parser.getAttributeValue(null, "href");
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = parser.getText();
                        if (text != null) {
                            text = text.trim();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        name = parser.getName();
                        if (currentFeedItem == null) {
                            if (name.equalsIgnoreCase("title")) {
                                feed.title = text;
                            } else if (name.equalsIgnoreCase("icon")) {
                                feed.iconUrl = text;
                            } else if (name.equalsIgnoreCase("link")) {
                                feed.link = text;
                            } else if (name.equalsIgnoreCase("atom:link")) {
                                feed.url = feedUrl;
                            }
                        } else {
                            if (name.equalsIgnoreCase("entry") || name.equalsIgnoreCase("item")) {
                                if (!currentFeedItem.content.isEmpty()) {
                                    List<String> imageUrls = HtmlUtil.getImageUrls(currentFeedItem.content);
                                    for (String url : imageUrls) {
                                        FeedItemImage image = new FeedItemImage();
                                        image.url = url;
                                        images.add(image);
                                    }
                                }
                                currentFeedItem.addImages(images);
                                feed.addEntry(currentFeedItem);
                                currentFeedItem = null;
                                images.clear();
                            } else if (name.equalsIgnoreCase("title")) {
                                currentFeedItem.title = text;
                            } else if (name.equalsIgnoreCase("content") || name.equalsIgnoreCase("description")) {
                                currentFeedItem.content = text;
                            } else if (name.equalsIgnoreCase("link")) {
                                if (link != null) {
                                    currentFeedItem.link = link;
                                    link = null;
                                } else {
                                    currentFeedItem.link = text;
                                }
                            } else if (name.equalsIgnoreCase("published") || name.equalsIgnoreCase("pubDate")) {
                                currentFeedItem.publishDate = text;
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

    private static Feed parseFeed(JSONObject object) throws JSONException {
        Feed feed = new Feed();
        feed.url = object.getString("url");
        feed.title = object.getString("title");
        feed.link = object.getString("link");
        feed.description = object.getString("description");
        feed.iconUrl = object.getString("icon");
        // TODO: tags

        return feed;
    }

    public interface FeedCallback {
        void onFailure(Call call, IOException e);
        void onResponse(Feed feed);
    }

    public interface FeedsCallback {
        void onFailure(Call call, IOException e);
        void onResponse(List<Feed> feeds);
    }
}
