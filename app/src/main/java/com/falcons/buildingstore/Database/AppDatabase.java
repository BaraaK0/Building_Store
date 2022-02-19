package com.falcons.buildingstore.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.falcons.buildingstore.Database.Daos.Customers_Dao;
import com.falcons.buildingstore.Database.Daos.Items_Dao;
import com.falcons.buildingstore.Database.Daos.OrdersDetails_Dao;
import com.falcons.buildingstore.Database.Daos.OrdersMaster_Dao;
import com.falcons.buildingstore.Database.Daos.TempOrders_Dao;
import com.falcons.buildingstore.Database.Daos.UserLogsDao;
import com.falcons.buildingstore.Database.Daos.Users_Dao;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.Database.Entities.TempOrder;
import com.falcons.buildingstore.Database.Entities.User;

@Database(entities = {Item.class, CustomerInfo.class, TempOrder.class, OrderMaster.class, User.class, OrdersDetails.class}, version = 10)
public abstract class AppDatabase extends RoomDatabase {

    public abstract Items_Dao itemsDao();

    public abstract Customers_Dao customersDao();

    public abstract TempOrders_Dao tempOrdersDao();

    public abstract OrdersMaster_Dao ordersMasterDao();

    public abstract Users_Dao usersDao();
    public abstract UserLogsDao userLogsDao();
    public abstract OrdersDetails_Dao ordersDetails_dao();

    private static AppDatabase InstanceDatabase;
    public static String DatabaseName = "BuildingStore_Database";
    static final Migration MIGRATION_8_9 = new Migration(8, 9) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


        }
    };
    static final Migration MIGRATION_9_10 = new Migration(9, 10) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {


        }
    };
    public static synchronized AppDatabase getInstanceDatabase(Context context) {

        if (InstanceDatabase == null) {

            InstanceDatabase = Room.databaseBuilder(context, AppDatabase.class, DatabaseName)
                    .allowMainThreadQueries().addMigrations(MIGRATION_8_9 ,MIGRATION_9_10)
                    .build();

        }

        return InstanceDatabase;

    }

}
