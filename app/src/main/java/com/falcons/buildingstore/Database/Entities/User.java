package com.falcons.buildingstore.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users_Table")
public class User {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "User_ID")
    public int userId;

    @ColumnInfo(name = "User_Name")
    public String userName;

    @ColumnInfo(name = "User_Password")
    public String userPassword;

    @ColumnInfo(name = "Disc_Permission")
    public String discPermission;

    public User(int userId, String userName, String userPassword, String discPermission) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.discPermission = discPermission;
    }

    public User(String userName, String userPassword, String discPermission) {
        this.userName = userName;
        this.userPassword = userPassword;
        this.discPermission = discPermission;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getDiscPermission() {
        return discPermission;
    }

    public void setDiscPermission(String discPermission) {
        this.discPermission = discPermission;
    }
}
