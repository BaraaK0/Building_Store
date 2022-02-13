package com.falcons.buildingstore.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.falcons.buildingstore.Database.Daos.Customers_Dao;
import com.falcons.buildingstore.Database.Daos.Items_Dao;
import com.falcons.buildingstore.Database.Daos.OrdersDetails_Dao;
import com.falcons.buildingstore.Database.Daos.OrdersMaster_Dao;
import com.falcons.buildingstore.Database.Daos.TempOrders_Dao;
import com.falcons.buildingstore.Database.Daos.Users_Dao;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.Database.Entities.TempOrder;
import com.falcons.buildingstore.Database.Entities.User;

@Database(entities = {Item.class, CustomerInfo.class, TempOrder.class, OrderMaster.class, User.class, OrdersDetails.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Items_Dao itemsDao();

    public abstract Customers_Dao customersDao();

    public abstract TempOrders_Dao tempOrdersDao();

    public abstract OrdersMaster_Dao ordersMasterDao();

    public abstract Users_Dao usersDao();
    public abstract OrdersDetails_Dao ordersDetails_dao();

    private static AppDatabase InstanceDatabase;
    public static String DatabaseName = "BuildingStore_Database";

    public static synchronized AppDatabase getInstanceDatabase(Context context) {

        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries()
                    .build();

        }

        return InstanceDatabase;

    }

}
