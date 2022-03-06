package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.Item_Unit_Details;

import java.util.List;

@Dao
public interface ItemUnits_Dao {

    @Insert
    void addAll(List<Item_Unit_Details> unitDetails);

    @Query("DELETE FROM Item_Unit_Details")
    void deleteAll();

    @Query("SELECT UNITID FROM Item_Unit_Details WHERE ITEMNO = :itemNo")
    List<String> getItemUnits(String itemNo);

    @Query("SELECT CONVRATE FROM Item_Unit_Details WHERE ITEMNO = :itemNo AND UNITID = :unitId")
    Double getConvRate(String itemNo, String unitId);
    @Query("SELECT CONVRATE FROM Item_Unit_Details WHERE ITEMNO = :itemNo")
    double getConvRatebyitemnum(String itemNo);

}
