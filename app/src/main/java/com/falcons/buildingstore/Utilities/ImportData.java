package com.falcons.buildingstore.Utilities;

import static android.content.Context.MODE_PRIVATE;
import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;
import static com.falcons.buildingstore.Activities.LoginActivity.coNoEdt;
import static com.falcons.buildingstore.Activities.LoginActivity.ipEdt;
import static com.falcons.buildingstore.Activities.LoginActivity.portEdt;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.falcons.buildingstore.Activities.LoginActivity;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.HttpHostConnectException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImportData {

    public List<Item> allItemsList;
    private Context context;
    public AppDatabase appDatabase;
    SharedPreferences sharedPref;
    public String headerDll = "", link = "";
    SweetAlertDialog pDialog, pDialog2;

    public ImportData(Context context) {

        this.context = context;
        appDatabase = AppDatabase.getInstanceDatabase(context);
        sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

//        headerDll = "/Falcons/VAN.Dll/";

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
                GeneralMethod.showSweetDialog(context, 1, "Customers Saved", null);
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
                GeneralMethod.showSweetDialog(context, 1, "Items Saved", null);
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

                }
                Log.e("getItems_Error", error.getMessage() + "");

            }
        });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(30 * 1000, 2, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);

    }

//
//    public void getAllItems() {
//
//        new JSONTask_getAllItems().execute();
//
//    }
//
//    private class JSONTask_getAllItems extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//
//            pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pDialog.getProgressHelper().setBarColor(Color.parseColor("#115571"));
//            pDialog.setTitleText("Loading ...");
//            pDialog.setCancelable(false);
//            pDialog.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//            link = "https://" + ipAddress + ":" + ipPort + headerDll + "/GetVanAllData?STRNO=1&CONO=" + coNo;
//            Log.e("getItems_link", "" + link);
//
//            try {
//
//                String JsonResponse;
//                HttpClient client = new DefaultHttpClient();
//                HttpGet getRequest = new HttpGet();
//                getRequest.setURI(new URI(link));
//
//                HttpResponse response = client.execute(getRequest);
//
//                BufferedReader in = new BufferedReader(new
//                        InputStreamReader(response.getEntity().getContent()));
//
//                StringBuilder sb = new StringBuilder("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//                    sb.append(line);
//                }
//
//                in.close();
//
//                JsonResponse = sb.toString();
//                Log.e("getItems_Response", JsonResponse);
//
//                return JsonResponse;
//
//
//            } catch (HttpHostConnectException ex) {
//                ex.printStackTrace();
//
//                Handler h = new Handler(Looper.getMainLooper());
//
//                h.post(() -> {
//                    pDialog.dismissWithAnimation();
//                    Toast.makeText(context, "Ip Connection Failed", Toast.LENGTH_LONG).show();
//                });
//
//                return null;
//            } catch (Exception e) {
//                pDialog.dismissWithAnimation();
//                e.printStackTrace();
//                return null;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            Log.e("getItems_result", "" + s);
//
//            pDialog.dismissWithAnimation();
//            if (s != null) {
//
//                try {
//                    JSONObject jsonObject;
//                    jsonObject = new JSONObject(s);
//                    Log.e("jsonObject_result", jsonObject.toString());
//
//                    JSONArray itemsArray = jsonObject.getJSONArray("Items_Master");
//
//                    for (int i = 0; i < itemsArray.length(); i++) {
//
//                        Item item = new Item();
//                        item.setItem_Name(itemsArray.getJSONObject(i).getString("NAME"));
//                        item.setItemNCode(itemsArray.getJSONObject(i).getString("BARCODE"));
//                        item.setItemOCode(itemsArray.getJSONObject(i).getString("ITEMNO"));
//                        item.setImagePath(itemsArray.getJSONObject(i).getString("ITEMPICSPATH"));
//                        item.setItemKind(itemsArray.getJSONObject(i).getString("ItemK"));
//                        item.setPrice(Double.parseDouble(itemsArray.getJSONObject(i).getString("MINPRICE")));
//                        item.setCategoryId(itemsArray.getJSONObject(i).getString("CATEOGRYID"));
//
//
//                        allItemsList.add(item);
//
//                    }
//
//                    appDatabase.itemsDao().addAll(allItemsList);
//
//                } catch (JSONException e) {
//                    pDialog.dismissWithAnimation();
//                    e.printStackTrace();
//                }
//            } else {
//
//                pDialog.dismissWithAnimation();
//
//            }
//
//        }
//    }


}