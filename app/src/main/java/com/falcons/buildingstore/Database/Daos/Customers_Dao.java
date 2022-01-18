package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.CustomerInfo;

import java.util.List;

@Dao
public interface Customers_Dao {

    @Query("SELECT * FROM Customers_Info")
    List<CustomerInfo> getAllCustms();

    @Insert
    void insertAllCustms(CustomerInfo... customerInfos);

    @Delete
    void deleteCustomer(CustomerInfo customerInfo);


}
