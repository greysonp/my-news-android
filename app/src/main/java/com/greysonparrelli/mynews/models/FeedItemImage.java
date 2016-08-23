package com.greysonparrelli.mynews.models;

import com.greysonparrelli.mynews.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
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
public class FeedItemImage extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    @ForeignKeyReference(columnName = "feed_item_id", foreignKeyColumnName = "id", columnType = FeedItem.class)
    public long feedItemId;

    @Column(name = "url")
    public String url;

}
