package com.greysonparrelli.mynews.models;

import com.greysonparrelli.mynews.data.AppDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKeyReference;
import com.raizlabs.android.dbflow.annotation.ManyToMany;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

/**
 * @author Greyson Parrelli (keybase.io/greyson)
 */
@Table(database = AppDatabase.class)
@ManyToMany(referencedTable = FeedItem.class)
public class FeedGroup extends BaseModel {

    @PrimaryKey(autoincrement = true)
    public long id;

    @Column
    public String title;

}
