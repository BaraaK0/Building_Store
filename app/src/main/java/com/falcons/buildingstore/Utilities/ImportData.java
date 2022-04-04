package com.falcons.buildingstore.Utilities;

import static android.content.Context.MODE_PRIVATE;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;



import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.util.Log;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.User;


import org.json.JSONArray;
import org.json.JSONObject;


import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImportData {

    public List<Item> allItemsList;
    private Context context;
    public AppDatabase appDatabase;
    SharedPreferences sharedPref;
    public String headerDll = "", link = "";
    private SweetAlertDialog pDialog, pDialog2, pDialog3;

    public ImportData(Context context) {

        this.context = context;
        appDatabase = AppDatabase.getInstanceDatabase(context);
        sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

//        headerDll = "/Falcons/VAN.Dll/";

    }

    public interface GetUsersCallBack {

        void onResponse(JSONArray response);

        void onError(String error);

    }

    public void getAllUsers(GetUsersCallBack getUsersCallBack, String ipAddress, String ipPort, String coNo) {

        pDialog3 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog3.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog3.setTitleText("Loading ...");
        pDialog3.setCancelable(false);
        pDialog3.show();
        if (!ipAddress.equals("") || !ipPort.equals("") || !coNo.equals(""))
            link = "http://" + ipAddress + ":" + ipPort + headerDll.trim() + "/ADMSALESMAN?CONO=" + coNo;

        Log.e("getUsers_Link", link);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                getUsersCallBack.onResponse(response);
                pDialog3.dismissWithAnimation();
//                GeneralMethod.showSweetDialog(context, 1, "Users Saved", null);
                Log.e("getUsers_Response", response + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getUsersCallBack.onError(error.getMessage() + "");
                pDialog3.dismissWithAnimation();
                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {

                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");

                } else if ((error.getMessage() + "").contains("ConnectException")) {

                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);

                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");

                } else if ((error.getMessage() + "").contains("No Data Found")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "No Users Found");
                    appDatabase.usersDao().insertUser(new User("010101", "admin", "2022", 1, 1, 0));

                }
                Log.e("getUsers_Error", error.getMessage() + "");

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

    public interface GetCustomersCallBack {

        void onResponse(JSONArray response);

        void onError(String error);

    }

    public void getAllCustomers(GetCustomersCallBack getCustomersCallBack, String ipAddress, String ipPort, String coNo) {

        pDialog2 = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog2.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog2.setTitleText("Loading ...");
        pDialog2.setCancelable(false);
        pDialog2.show();
        if (!ipAddress.equals("") || !ipPort.equals("") || !coNo.equals(""))
            link = "http://" + ipAddress + ":" + ipPort + headerDll + "/ADMGetCustomer?CONO=" + coNo;
//        else
//            link = "http://" + ipEdt.getText().toString().trim() + ":" +
//                    portEdt.getText().toString().trim() +
//                    headerDll + "/ADMGetCustomer?CONO=" + coNoEdt.getText().toString().trim();

        Log.e("getCustms_Link", link);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(link, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                getCustomersCallBack.onResponse(response);
                pDialog2.dismissWithAnimation();
//                GeneralMethod.showSweetDialog(context, 1, "Customers Saved", null);
                Log.e("getCustms_Response", response + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getCustomersCallBack.onError(error.getMessage() + "");
                pDialog2.dismissWithAnimation();
                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {

                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");

                } else if ((error.getMessage() + "").contains("ConnectException")) {

                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);

                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");

                } else if ((error.getMessage() + "").contains("No Data Found")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "No Customers Found");

                }
                Log.e("getCustms_Error", error.getMessage() + "");

            }
        });

        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(20 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonArrayRequest);

    }

    public interface GetItemsCallBack {

        void onResponse(JSONObject response);

        void onError(String error);

    }

    public void getAllItems(GetItemsCallBack getItemsCallBack, String ipAddress, String ipPort, String coNo) {

        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog.setTitleText("Loading ...");
        pDialog.setCancelable(false);
        pDialog.show();
        //http://10.0.0.22:8085/GetVanAllData?STRNO=1&CONO=295
        if (!ipAddress.equals("") || !ipPort.equals("") || !coNo.equals(""))
            link = "http://" + ipAddress + ":" + ipPort + headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNo;
//        else
//            link = "http://" + ipEdt.getText().toString().trim() + ":" +
//                    portEdt.getText().toString().trim() +
//                    headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNoEdt.getText().toString().trim();

        Log.e("getItems_Link", link);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                getItemsCallBack.onResponse(response);
                pDialog.dismissWithAnimation();
//                GeneralMethod.showSweetDialog(context, 1, "Items Saved", null);
                Log.e("getItems_Response", response + "");

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                getItemsCallBack.onError(error.getMessage() + "");
                pDialog.dismissWithAnimation();
                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {

                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");

                } else if ((error.getMessage() + "").contains("ConnectException")) {

                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);

                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");

                } else if ((error.getMessage() + "").contains("No Data Found")) {

                    GeneralMethod.showSweetDialog(context, 3, "", "No Items Found");

                }
                Log.e("getItems_Error", error.getMessage() + "");

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }


//    public void getUnits(GetItemsCallBack getItemsCallBack, String ipAddress, String ipPort, String coNo) {
//
//        pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//
//        pDialog.getProgressHelper().setBarColor(Color.parseColor("#115571"));
//        pDialog.setTitleText("Loading ...");
//        pDialog.setCancelable(false);
//        pDialog.show();
//        //http://10.0.0.22:8085/GetVanAllData?STRNO=1&CONO=295
//        if (!ipAddress.equals("") || !ipPort.equals("") || !coNo.equals(""))
//            link = "http://" + ipAddress + ":" + ipPort + headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNo;
////        else
////            link = "http://" + ipEdt.getText().toString().trim() + ":" +
////                    portEdt.getText().toString().trim() +
////                    headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNoEdt.getText().toString().trim();
//
//        Log.e("getItems_Link", link);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(link, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//                getItemsCallBack.onResponse(response);
//                pDialog.dismissWithAnimation();
////                GeneralMethod.showSweetDialog(context, 1, "Items Saved", null);
//                Log.e("getItems_Response", response + "");
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                getItemsCallBack.onError(error.getMessage() + "");
//                pDialog.dismissWithAnimation();
//                if ((error.getMessage() + "").contains("SSLHandshakeException") || (error.getMessage() + "").equals("null")) {
//
//                    GeneralMethod.showSweetDialog(context, 0, null, "Connection to server failed");
//
//                } else if ((error.getMessage() + "").contains("ConnectException")) {
//
//                    GeneralMethod.showSweetDialog(context, 0, "Connection Failed", null);
//
//                } else if ((error.getMessage() + "").contains("NoRouteToHostException")) {
//
//                    GeneralMethod.showSweetDialog(context, 3, "", "Please check the entered IP info");
//
//                } else if ((error.getMessage() + "").contains("No Data Found")) {
//
//                    GeneralMethod.showSweetDialog(context, 3, "", "No Items Found");
//
//                }
//                Log.e("getItems_Error", error.getMessage() + "");
//
//            }
//        });
//
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
//
//    }


}
