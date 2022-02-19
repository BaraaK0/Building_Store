package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.User;

import java.util.List;

@Dao
public interface Users_Dao {

    @Query("SELECT * FROM Users_Table")
    List<User> getAllUsers();

//    @Query("SELECT * FROM Users_Table WHERE User_ID IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM Users_Table WHERE User_Name LIKE :name")
//    User findByName(String name);

    @Insert
    void insertAllUsers(User... users);
    @Insert
    void insertUser(User user);
    @Delete
    void deleteUser(User user);

    @Query("SELECT Disc_Permission FROM Users_Table where User_ID= :id")
    int getuserPer ( int id);



}
