package com.greysonparrelli.mynews.models;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
public class Feed {

    private String mTitle;
    private String mIconUrl;
    private String mLink;
    private List<FeedItem> mEntries;

    public Feed() {
        mEntries = new ArrayList<>();
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getIconUrl() {
        return mIconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.mIconUrl = iconUrl;
    }

    public void addEntry(FeedItem feedItem) {
        mEntries.add(feedItem);
    }

    public List<FeedItem> getEntries() {
        return mEntries;
    }

    @Override
    public String toString() {
        return "Title: " + getTitle() + " | " + getEntries().toString();
    }
}
