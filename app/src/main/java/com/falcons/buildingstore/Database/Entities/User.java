package com.falcons.buildingstore.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users_Table")
public class User {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "User_ID")
    private String userId;

    @ColumnInfo(name = "User_Name")
    private String userName;

    @ColumnInfo(name = "User_Password")
    private String userPassword;

    @ColumnInfo(name = "User_Type")
    private int userType;

    @ColumnInfo(name = "Disc_Permission")
    private int discPermission;

    public User(String userId, String userName, String userPassword, int userType, int discPermission) {
        this.userId = userId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userType = userType;
        this.discPermission = discPermission;
    }

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
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

    public int getDiscPermission() {
        return discPermission;
    }

    public void setDiscPermission(int discPermission) {
        this.discPermission = discPermission;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }
}
