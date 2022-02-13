package com.falcons.buildingstore.DataManipulation;

import static android.content.Context.MODE_PRIVATE;
import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.falcons.buildingstore.Database.AppDatabase;

public class ImportData {

    private Context context;
    public AppDatabase appDatabase;
    SharedPreferences sharedPref;
    public String ipAddress = "", coNo = "", headerDll = "", link = "", ipPort = "";

    public ImportData(Context context) {

        this.context = context;
        appDatabase = AppDatabase.getInstanceDatabase(context);
        sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");

//        headerDll = "/Falcons/VAN.Dll/";

    }




}
