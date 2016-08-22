package com.greysonparrelli.mynews.models;

import com.greysonparrelli.mynews.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.OneToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.structure.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
@Table(database = AppDatabase.class)
public class Feed extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column(name = "title")
    public String title;

    @Column(name = "icon_url")
    public String iconUrl;

    @Column(name = "link")
    public String link;

    List<FeedItem> entries;

    public Feed() {
        entries = new ArrayList<>();
    }

    public void addEntry(FeedItem feedItem) {
        entries.add(feedItem);
    }

    @OneToMany(methods = {OneToMany.Method.ALL})
    public List<FeedItem> getEntries() {
        if (entries == null || entries.isEmpty()) {
            entries = SQLite.select()
                    .from(FeedItem.class)
                    .where(FeedItem_Table.feedId.eq(id))
                    .queryList();
        }
        return entries;
    }
}
