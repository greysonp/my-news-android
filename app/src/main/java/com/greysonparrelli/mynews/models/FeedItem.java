package com.greysonparrelli.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.greysonparrelli.mynews.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
@Table(database = AppDatabase.class)
public class FeedItem extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    @ForeignKeyReference(columnName = "feed_id", foreignKeyColumnName = "id", columnType = Feed.class)
    public long feedId;

    @Column
    public String title;

    @Column
    public String content;

    @Column
    @Unique
    public String link;

    @Column
    public String publishDate;

    public FeedItem() {}

    protected FeedItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        link = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(content);
        parcel.writeString(link);
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
