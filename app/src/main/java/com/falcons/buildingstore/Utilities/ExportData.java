package com.falcons.buildingstore.Utilities;

import static android.content.Context.MODE_PRIVATE;
import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;
import static com.falcons.buildingstore.Utilities.GeneralMethod.showSweetDialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.Database.Entities.User;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.google.gson.JsonArray;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExportData {

    List<OrderMaster> vouchers;
    private Context context;
    List<OrdersDetails> items;
    private AppDatabase appDatabase;
    private String URL_TO_HIT, ipAddress, ipPort, headerDll = "", coNo = "";
    private ProgressDialog progressDialog, pdValidation;
    SweetAlertDialog progressSave, pdVoucher;
    AppDatabase mHandler;
    JSONObject vouchersObject;
    private JSONArray jsonArrayVouchers, jsonArrayItems;

    public ExportData(Context context) {
        this.context = context;
        this.mHandler = AppDatabase.getInstanceDatabase(context);

        appDatabase = AppDatabase.getInstanceDatabase(context);

        SharedPreferences sharedPref = context.getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
    }

    private JSONObject getAddUserObj(List<User> userList) {

        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < userList.size(); i++) {

            jsonArray.put(userList.get(i).getJSONObject(true));

        }

        JSONObject addUserObj = new JSONObject();
        try {
            addUserObj.put("JSN", jsonArray);
            Log.e("AddUser_Obj", addUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addUserObj;
    }

    public interface AddUserCallBack {

        void onResponse(String response);

        void onError(String error);

    }

    public void addUser(List<User> userList, AddUserCallBack addUserCallBack) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog.setTitleText("Saving ...");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = "http://" + ipAddress + ":" + ipPort + headerDll + "/ADMAddSalesMan";
        Log.e("AddUser_URL ", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pDialog.dismissWithAnimation();
                Log.e("AddUser_Response", response);
                addUserCallBack.onResponse(response+"");
                if (response.contains("Saved Successfully")) {

                    appDatabase.usersDao().setPosted();


                } else if (response.contains("server error")) {

                    showSweetDialog(context, 0, "Internal server error", null);

                } else if (response.contains("unique constraint")) {

                    Log.e("unique response", response.toString() + "");
                    try {
                        showSweetDialog(context, 0, "Unique Constraint", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismissWithAnimation();
                showSweetDialog(context, 0, "Connection Failed", null);
                addUserCallBack.onError(error.getMessage()+"");
                VolleyLog.e("Error: ", error.getMessage()+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<>();

                params.put("CONO", coNo);
                params.put("JSONSTR", getAddUserObj(userList).toString());

                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);

    }

    private JSONObject getAddCustObj(List<CustomerInfo> customerList) {

        JSONArray jsonArray = new JSONArray();

        UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();

        for (int i = 0; i < customerList.size(); i++) {

            jsonArray.put(customerList.get(i).getJSONObject(userLogs.getUserName(), userLogs.getUserID()));

        }

        JSONObject addUserObj = new JSONObject();
        try {
            addUserObj.put("JSN", jsonArray);
            Log.e("AddCustomer_Obj", addUserObj.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return addUserObj;
    }

    public interface AddCustCallBack {

        void onResponse(String response);

        void onError(String error);

    }

    public void addCustomer(List<CustomerInfo> customerList, AddCustCallBack addCustCallBack) {

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);

        pDialog.getProgressHelper().setBarColor(Color.parseColor("#115571"));
        pDialog.setTitleText("Saving ...");
        pDialog.setCancelable(false);
        pDialog.show();

        String url = "http://" + ipAddress + ":" + ipPort + headerDll + "/ExportADDED_CUSTOMERS";
        Log.e("AddUser_URL ", url);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                pDialog.dismissWithAnimation();
                addCustCallBack.onResponse(response);
                Log.e("AddCustomer_Response", response);
                if (response.contains("Saved Successfully")) {

                    appDatabase.customersDao().setPosted();

//                    showSweetDialog(context, 1, context.getString(R.string.savedSuccsesfule), null);

                } else if (response.contains("server error")) {

                    showSweetDialog(context, 0, "Internal server error", null);

                } else if (response.contains("unique constraint")) {

                    Log.e("unique response", response.toString() + "");
                    try {
                        showSweetDialog(context, 0, "Unique Constraint", null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismissWithAnimation();
                addCustCallBack.onError(error.getMessage()+"");
                showSweetDialog(context, 0, "Connection Failed", "");

                VolleyLog.e("Error: ", error.getMessage()+"");
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<>();

                params.put("CONO", coNo);
                params.put("JSONSTR", getAddCustObj(customerList).toString());

                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });
        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);

    }


    private void exportVoucherDetail() {//2
        getVouchersDetail();
        new JSONTaskDelphiDetail().execute();
    }

    private void saveExpot() {
//        new JSONTaskSaveVouchers().execute();

    }

    public void exportSalesVoucherM() {
        getVouchers();

        pdVoucher = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        pdVoucher.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
        pdVoucher.setTitleText(" Start export Vouchers");
        pdVoucher.setCancelable(false);
        pdVoucher.show();
        new JSONTaskDelphi().execute();

    }

    private void updateVoucherExported() {// 3
        Log.e("updateVoucherExported", "trueee");
        mHandler.ordersMasterDao().updateVoucher();
        mHandler.ordersDetails_dao().updateVoucherDetails();
        Log.e("onPostExecute", "updateVoucherExported---3---");
    }

    private void getVouchersDetail() {
        items = mHandler.ordersDetails_dao().getAllOrders();
        jsonArrayItems = new JSONArray();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i).getIsPosted() == 0) {

                jsonArrayItems.put(items.get(i).getJSONObjectDelphi());

            }

        }
        try {
            vouchersObject = new JSONObject();
            vouchersObject.put("JSN", jsonArrayItems);
            Log.e("getVouchersDetail", "" + vouchersObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getVouchers() {

        vouchers = mHandler.ordersMasterDao().getAllOrders();// from voucher master
        jsonArrayVouchers = new JSONArray();
        for (int i = 0; i < vouchers.size(); i++) {
            if (vouchers.get(i).getIsPosted() == 0) {
                jsonArrayVouchers.put(vouchers.get(i).getJSONObjectDelphi());
            }
        }
        try {
            vouchersObject = new JSONObject();
            vouchersObject.put("JSN", jsonArrayVouchers);
            Log.e("vouchersObject", "" + vouchersObject.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private class JSONTaskDelphiDetail extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;
//        SweetAlertDialog pdItem=null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pdItem = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            pdItem.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            pdItem.setTitleText("export");
//            pdItem.setCancelable(false);
//            pdItem.show();
        }

        @Override
        protected String doInBackground(String... params) {

//                try {
            //  URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/ExportSALES_VOUCHER_D?CONO="+CONO.trim()+"&JSONSTR="+vouchersObject.toString().trim();

//
            String link = "http://" + ipAddress.trim() + ":" + ipPort.trim() + headerDll.trim() + "/ExportSALES_VOUCHER_D";
            Log.e("link==", link + "");


            String ipAddress = "";
            Log.e("tagexPORT", "JsonResponse");


            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(link));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", coNo.trim()));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", vouchersObject.toString().trim()));
                Log.e("nameValuePairs", "Details=JSONSTR" + vouchersObject.toString().trim());


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("JsonResponse", "ExportSerial" + JsonResponse);


                //*******************************************


            } catch (Exception e) {
            }
            return JsonResponse;
        }


        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
            // Log.e("onPostExecute","---2---"+result);
//            pdItem.dismiss();
            // Log.e("onPostExecute","ExportSALES_VOUCHER_D"+result);
            pdVoucher.setTitle("Export SALES_VOUCHER_Detail");
            if (result != null && !result.equals("")) {
                if (result.contains("Saved Successfully")) {
//                    Toast.makeText(context, "onPostExecute"+result, Toast.LENGTH_SHORT).show();


                    updateVoucherExported();// 3
                    pdVoucher.dismissWithAnimation();

                }


            } else {
                pdVoucher.dismissWithAnimation();
//                Toast.makeText(context, "onPostExecute", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class JSONTaskDelphi extends AsyncTask<String, String, String> {
        private String JsonResponse = null;
        private HttpURLConnection urlConnection = null;
        private BufferedReader reader = null;

        public String salesNo = "", finalJson;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                if (!ipAddress.equals("")) {


                    //  String data= "{\"JSN\":[{\"COMAPNYNO\":290,\"VOUCHERYEAR\":\"2021\",\"VOUCHERNO\":\"1212\",\"VOUCHERTYPE\":\"3\",\"VOUCHERDATE\":\"24/03/2020\",\"SALESMANNO\":\"5\",\"CUSTOMERNO\":\"123456\",\"VOUCHERDISCOUNT\":\"50\",\"VOUCHERDISCOUNTPERCENT\":\"10\",\"NOTES\":\"AAAAAA\",\"CACR\":\"1\",\"ISPOSTED\":\"0\",\"PAYMETHOD\":\"1\",\"NETSALES\":\"150.720\"}]}";

                    URL_TO_HIT = "http://" + ipAddress.trim() + ":" + ipPort.trim() + headerDll.trim() + "/ExportSALES_VOUCHER_M";


                    Log.e("URL_TO_HIT", "" + URL_TO_HIT);
                }
            } catch (Exception e) {
                //progressDialog.dismiss();

            }

//********************************************************
            String ipAddress = "";
            Log.e("tagexPORT", "JsonResponse");


            try {
                HttpClient client = new DefaultHttpClient();
                HttpPost request = new HttpPost();
                try {
                    request.setURI(new URI(URL_TO_HIT));
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                    Handler h = new Handler(Looper.getMainLooper());
                    h.post(new Runnable() {
                        public void run() {
                            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("check Connection")
                                    .show();


//                        Toast.makeText(context, "check Connection", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("CONO", coNo.trim()));
                nameValuePairs.add(new BasicNameValuePair("JSONSTR", vouchersObject.toString().trim()));
                Log.e("nameValuePairs", "JSONSTR" + vouchersObject.toString().trim());


                request.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));


                HttpResponse response = client.execute(request);


                BufferedReader in = new BufferedReader(new
                        InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }

                in.close();


                JsonResponse = sb.toString();
                Log.e("JsonResponse", "ExporVoucher" + JsonResponse);


                //*******************************************


            } catch (Exception e) {
            }
            return JsonResponse;

        }


        @Override
        protected void onPostExecute(final String result) {
            super.onPostExecute(result);
//            progressDialog.dismiss();
            Log.e("onPostExecute", "---1---" + result);

            if (result != null && !result.equals("")) {
                if (result.contains("Saved Successfully")) {

                    exportVoucherDetail();// 2
                } else {
                    pdVoucher.dismissWithAnimation();
                }
//                Toast.makeText(context, "onPostExecute"+result, Toast.LENGTH_SHORT).show();


            } else {
                pdVoucher.dismissWithAnimation();
                Handler h = new Handler(Looper.getMainLooper());
                h.post(new Runnable() {
                    public void run() {
                        new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                                .setTitleText("check Connection")
                                .show();

                    }
                });
            }
        }
    }

}