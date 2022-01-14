package com.example.jobalifproject;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

@Entity(tableName = "model_table")

public class Model {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "url")
    private String url;
    @ColumnInfo(name = "endDate")
    private String endDate;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "icon")
    private String icon;


    public String getUrl() {
        return url;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
