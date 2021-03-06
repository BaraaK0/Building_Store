package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;

import java.util.ArrayList;
import java.util.List;
@Dao
public interface OrdersDetails_Dao {

    @Query("SELECT * FROM Orders_Details Where IS_Posted='0'")
    List<OrdersDetails> getAllOrders();
    @Query("SELECT * FROM Orders_Details Where IS_Posted='0' and ConfirmState='1'")
    List<OrdersDetails> getAllOrdersConfirm();
    @Query("SELECT * FROM Orders_Details Where VHFNO= :VHFNO and IS_Posted='0'")
    List<OrdersDetails> getAllOrdersByNumber(int VHFNO );
    @Insert
    void insertAllOrders(OrdersDetails  ordersDetails);
    @Insert
    void insertOrder(OrdersDetails  ordersDetails);

    @Delete
    void deleteOrder(OrdersDetails ordersDetails);

    @Query("UPDATE Orders_Details SET  IS_Posted='1' WHERE IS_Posted='0' AND ConfirmState ='1'")
  int  updateVoucherDetails ();

    @Query("delete from Orders_Details where VHFNO= :vohno")
    int deleteOrderByVOHNO(int vohno);
}
