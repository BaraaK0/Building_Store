package com.falcons.buildingstore.Database.Entities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.falcons.buildingstore.Database.AppDatabase;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;

class ImportData {
    private Context context;
    private AppDatabase appDatabase;
    private String URL_TO_HIT,ipAddress,ipWithPort,headerDll,CONO;
    private ProgressDialog progressDialog;
    public static ArrayList<Item> itemSerialList = new ArrayList<>();
    AppDatabase mHandler;
    public ImportData(Context context) {
        this.context = context;
        this.mHandler = AppDatabase.getInstanceDatabase(context);

        ipAddress=   IP_PREF;
        ipWithPort=PORT_PREF;

    }

//




}
