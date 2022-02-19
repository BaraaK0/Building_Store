package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.User;
import com.falcons.buildingstore.Database.Entities.UserLogs;

@Dao
public interface UserLogsDao {

    @Insert
    void insertUser(UserLogs userLogs);

    @Query("SELECT * FROM UserLogs where SERIAL= (SELECT MAX(SERIAL) FROM UserLogs)")
    UserLogs getLastuserLogin ( );
}
