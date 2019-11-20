package com.example.beenlovememory;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    List<User> getAll();
    @Insert
    void insertAll(User... users);

    @Update
    void updateUser(User user);

    @Query("UPDATE user SET name1=:name1 WHERE uid=0")
    void updateName1(String name1);
    @Query("UPDATE user SET name2=:name2 WHERE uid=0")
    void updateName2(String name2);
    @Query("UPDATE user SET date=:date WHERE uid=0")
    void updateDate(String date);

   // void updateUser(String name1, String name2, String date);
}