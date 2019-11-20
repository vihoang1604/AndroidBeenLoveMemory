package com.example.beenlovememory;

import java.util.Date;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name1;
    private String name2;
    private String date;
    public User(int uid, String name1, String name2, String date) {
        this.uid = uid;
        this.name1 = name1;
        this.name2 = name2;
        this.date = date;
    }
    public User() {

    }
    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getUid() {
        return uid;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }

    public String getDate() {
        return date;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public void setDate(String date) {
        this.date = date;
    }
}