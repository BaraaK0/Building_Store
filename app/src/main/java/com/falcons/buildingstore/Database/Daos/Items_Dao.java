package com.falcons.buildingstore.Database.Daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.falcons.buildingstore.Database.Entities.Item;

import java.util.List;

@Dao
public interface Items_Dao {

    @Query("SELECT * FROM Items_Table")
    List<Item> getAllItems();

    @Insert
    void insertAllItems(Item... items);

    @Delete
    void deleteItem(Item item);


    @Query("UPDATE Items_Table SET  area= :aria WHERE Item_OCode= :itemcode")
    void UpdateItemAria(String aria,String itemcode);
}
