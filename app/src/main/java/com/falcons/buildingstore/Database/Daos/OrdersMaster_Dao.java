package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.OrderMaster;

import java.util.List;

@Dao
public interface OrdersMaster_Dao {

    @Query("SELECT * FROM Orders_Master")
    List<OrderMaster> getAllOrders();

    @Insert
    void insertAllOrders(OrderMaster... orderMasters);
    @Insert
    void insertOrder(OrderMaster  orderMasters);

    @Delete
    void deleteOrder(OrderMaster orderMaster);
    @Query("UPDATE Orders_Master SET  IS_Posted='1' WHERE IS_Posted='0' ")
    int updateVoucher();
}
