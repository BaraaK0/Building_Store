package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.OrderMaster;

import java.util.List;

@Dao
public interface OrdersMaster_Dao {

    @Query("SELECT * FROM Orders_Master Where IS_Posted='0'")
    List<OrderMaster> getAllOrders();
    @Query("SELECT * FROM Orders_Master Where IS_Posted='0' and ConfirmState='1'")
    List<OrderMaster> getAllOrdersConfirm();
    @Insert
    void insertAllOrders(OrderMaster... orderMasters);
    @Insert
    void insertOrder(OrderMaster  orderMasters);

    @Delete
    void deleteOrderByVOHNO(OrderMaster orderMaster);
    @Query("UPDATE Orders_Master SET  IS_Posted='1' WHERE IS_Posted='0' AND ConfirmState ='1'")
    int updateVoucher();

    @Query("SELECT * FROM Orders_Master where Customer_ID= :cusid and VHFNO= :orderno and Date= :date and IS_Posted='0'")
    List<OrderMaster> getOrdersByOrderNOandCusID(String orderno,int cusid,String date);

    @Query("SELECT * FROM Orders_Master where VHFNO= :orderno and Date= :date and IS_Posted='0'")
    List<OrderMaster> getOrdersByOrderNO(String orderno,String date);

    @Query("SELECT * FROM Orders_Master where Customer_ID= :cusid and Date= :date and IS_Posted='0'")
    List<OrderMaster> getOrdersByCusID(int cusid,String date);

    @Query("SELECT * FROM Orders_Master where Date= :date and IS_Posted='0'")
    List<OrderMaster> getOrdersByDate (String date);



    @Query("SELECT VHFNO FROM Orders_Master where VHFNO= (SELECT MAX(VHFNO) FROM Orders_Master) and IS_Posted='0'")
    int getLastVoherNo ( );
    @Query("delete from Orders_Master where VHFNO= :vohno")
    int deleteOrderByVOHNO(int vohno);



}
