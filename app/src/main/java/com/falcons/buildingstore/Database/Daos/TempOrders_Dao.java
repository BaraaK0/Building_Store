package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.TempOrder;

import java.util.List;

@Dao
public interface TempOrders_Dao {

    @Query("SELECT * FROM Temp_Orders")
    List<TempOrder> getAllOrders();

    @Insert
    void insertAllOrders(TempOrder... tempOrders);
    @Insert
    void insertOrder(TempOrder tempOrders);

    @Delete
    void deleteOrder(TempOrder tempOrder);

}
