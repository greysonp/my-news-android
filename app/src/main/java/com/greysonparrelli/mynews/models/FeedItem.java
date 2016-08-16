package com.greysonparrelli.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

public class FeedItem implements Parcelable {

    private String mTitle;
    private String mContent;
    private String mLink;

    public FeedItem() {}

    protected FeedItem(Parcel in) {
        mTitle = in.readString();
        mContent = in.readString();
        mLink = in.readString();
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        this.mContent = content;
    }

    public String getLink() {
        return mLink;
    }

    public void setLink(String link) {
        this.mLink = link;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mTitle);
        parcel.writeString(mContent);
        parcel.writeString(mLink);
    }

    public static final Creator<FeedItem> CREATOR = new Creator<FeedItem>() {
        @Override
        public FeedItem createFromParcel(Parcel in) {
            return new FeedItem(in);
        }

        @Override
        public FeedItem[] newArray(int size) {
            return new FeedItem[size];
        }
    };
}
