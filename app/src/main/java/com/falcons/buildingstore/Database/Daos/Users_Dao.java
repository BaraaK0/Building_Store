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

    @Insert
    void insertAllUsers(User... users);

    @Insert
    void addAll(List<User> users);

    @Query("DELETE FROM Users_Table")
    void deleteAll();

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("SELECT Disc_Permission FROM Users_Table where User_Name = :username")
    int getuserPer(String username);

    @Query("SELECT User_Type FROM Users_Table where User_Name = :username")
    int getUserType(String username);

    @Query("UPDATE Users_Table SET Is_Posted = '1' WHERE Is_Posted = '0'")
    void setPosted();

    @Query("SELECT COUNT(User_Name) FROM Users_Table WHERE User_Name = :username")
    int getSameUsers(String username);

//    @Query("SELECT * FROM Users_Table WHERE User_ID IN (:userIds)")
//    List<User> loadAllByIds(int[] userIds);
//
//    @Query("SELECT * FROM Users_Table WHERE User_Name LIKE :name")
//    User findByName(String name);

}
