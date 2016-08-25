package com.greysonparrelli.mynews.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.greysonparrelli.mynews.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ConflictAction;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.annotation.Unique;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
@Table(database = AppDatabase.class)
public class FeedItem extends BaseModel implements Parcelable {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    @ForeignKey(tableClass = Feed.class)
    public Feed feed;

    @Column
    public String title;

    @Column
    public String content;

    @Unique(onUniqueConflict = ConflictAction.IGNORE)
    @Column
    public String link;

    @Column
    public String publishDate;

    List<FeedItemImage> images;

    public FeedItem() {
        images = new ArrayList<>();
    }

    protected FeedItem(Parcel in) {
        id = in.readLong();
        title = in.readString();
        content = in.readString();
        link = in.readString();
        images = new ArrayList<>();
    }

    public void addImages(List<FeedItemImage> images) {
        this.images.addAll(images);
    }

    @OneToMany(methods = {OneToMany.Method.ALL})
    public List<FeedItemImage> getImages() {
        if (images == null || images.isEmpty()) {
            images = SQLite.select()
                    .from(FeedItemImage.class)
                    .where(FeedItemImage_Table.feedItem_id.eq(id))
                    .queryList();
        }
        return images;
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
