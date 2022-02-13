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

//    private class JSONTaskDelphi extends AsyncTask<String, String, String> {
//
//        public String salesNo = "";
//
//        public JSONTaskDelphi(String sales) {
//            this.salesNo = sales;
//            Log.e("JSONTask", "salesNo" + salesNo);
//
//        }
//
//        @Override
//        protected void onPreExecute() {
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setCancelable(false);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setProgress(0);
//            progressDialog.show();
//        }
//
//        @Override
//        protected void doInBackground(String... params) {
//            URLConnection connection = null;
//            BufferedReader reader = null;
//
//            try {
//
//                try {
//
//                    //+custId
//
//                    if (!ipAddress.equals("")) {
//                           if (ipAddress.contains(":")) {
//                            int ind = ipAddress.indexOf(":");
//                            ipAddress = ipAddress.substring(0, ind);
//                        }
////
//                        if (!salesNo.equals("")) {
//                            URL_TO_HIT = "http://" + ipAddress.trim() + ":" + ipWithPort.trim() + headerDll.trim() + "/GetVanAllData?STRNO=" + salesNo + "&CONO=" + CONO;
//
//                        } else {
//                            URL_TO_HIT = "http://" + ipAddress.trim() + ":" + ipWithPort.trim() + headerDll.trim() + "/GetVanAllData?STRNO=" + 1 + "&CONO=" + CONO;
//
//                        }
//
//                        Log.e("URL_TO_HIT", "" + URL_TO_HIT);
//                    }
//                } catch (Exception e) {
//
//                }
//
//
//                String link = URL_TO_HIT;
//                URL url = new URL(link);
//
//                //*************************************
//
//                String JsonResponse = null;
//                StringBuffer sb = new StringBuffer("");
//                HttpClient client = new DefaultHttpClient();
//                HttpGet request = new HttpGet();
//                request.setURI(new URI(URL_TO_HIT));
//
//
//                HttpResponse response = null;
//
//                try {
//                    response = client.execute(request);
//                } catch (Exception e) {
//                    // Log.e("response",""+response.toString());
//                    Handler h = new Handler(Looper.getMainLooper());
//                    h.post(new Runnable() {
//                        public void run() {
//                            progressDialog.dismiss();
//
//                        }
//                    });
//                }
//
//
//                try {
//                    BufferedReader in = new BufferedReader(new
//                            InputStreamReader(response.getEntity().getContent()));
//
//
//                    String line = "";
//                    // Log.e("finalJson***Import", sb.toString());
//
//                    while ((line = in.readLine()) != null) {
//                        sb.append(line);
//                    }
//
//                    in.close();
//                } catch (Exception e) {
//                    Handler h = new Handler(Looper.getMainLooper());
//                    h.post(new Runnable() {
//                        public void run() {
//                            progressDialog.dismiss();
//
//                        }
//                    });
//                }
//
//
//                // JsonResponse = sb.toString();
//
//                String finalJson = sb.toString();
//                Log.e("finalJson***Import", finalJson);
//                String rate_customer = "";
//                String HideVal = "";
//
//                JSONObject parentObject = new JSONObject(finalJson);
//
//
//
//
//
//
//
//                try {
////                    `ITEMPICSPATH`
//                    JSONArray parentArrayItems_Master = parentObject.getJSONArray("Items_Master");
////                Log.e("parentArrayItems_Master",""+parentArrayItems_Master.getString(""));
//                    itemSerialList.clear();
//                    for (int i = 0; i < parentArrayItems_Master.length(); i++) {
//                        JSONObject finalObject = parentArrayItems_Master.getJSONObject(i);
//                        Item item = new Item();
//                    //    item.setCompanyNo(finalObject.getString("COMAPNYNO"));
//                        item.setItemNCode(finalObject.getString("ITEMNO"));
//                        item.setItemName(finalObject.getString("NAME"));
//                        item.setCategoryId(Double.parseDouble(finalObject.getString("CATEOGRYID")));
//                        item.setItemNCode(finalObject.getString("BARCODE"));
////                    item.setIsSuspended(finalObject.getInt("IsSuspended"));MINPRICE
//                        item.setPrice(finalObject.getDouble("F_D"));
//
//
//
//
//                        try {
//                            item.setItemHasSerial(finalObject.getString("ITEMHASSERIAL"));
//                            // Log.e("setItemHasSerialJSON", "" + finalObject.getString("ITEMHASSERIAL"));
//                        } catch (Exception e) {
//                        }
//                        try {
//                            if (finalObject.getString("ITEMPICSPATH") == "" || finalObject.getString("ITEMPICSPATH") == null || finalObject.getString("ITEMPICSPATH") == "null") {
//                                item.setPhotoItem("");
//                            } else {
//                                item.setPhotoItem(finalObject.getString("ITEMPICSPATH"));
//                            }
//
//
//                        } catch (Exception e) {
//                            item.setPhotoItem("");
//                        }
////                    ITEMPICSPATH
//                        itemsMasterList.add(item);
//                    }
//                } catch (JSONException e) {
//                    Log.e("Import Data", e.getMessage().toString());
//                }
//
//
//                try {
//                    JSONArray parentArrayPrice_List_M = parentObject.getJSONArray("Price_List_M");
//                    priceListMpList.clear();
//                    for (int i = 0; i < parentArrayPrice_List_M.length(); i++) {
//                        JSONObject finalObject = parentArrayPrice_List_M.getJSONObject(i);
//
//                        PriceListM item = new PriceListM();
//                        item.setCompanyNo(finalObject.getString("COMAPNYNO"));
//                        item.setPrNo(finalObject.getInt("PRNO"));
//                        item.setDescribtion(finalObject.getString("DESCRIPTION"));
//                        item.setIsSuspended(0);
////                    item.setIsSuspended(finalObject.getInt("IsSuspended"));
//
//                        priceListMpList.add(item);
//                    }
//
//                } catch (JSONException e) {
//                    Log.e("Import Data", e.getMessage().toString());
//                }
//
//
//                try {
//
//                    JSONArray parentArraySales_Team = parentObject.getJSONArray("Sales_Team");
//                    salesTeamList.clear();
//                    for (int i = 0; i < parentArraySales_Team.length(); i++) {
//                        JSONObject finalObject = parentArraySales_Team.getJSONObject(i);
//
//                        SalesTeam item = new SalesTeam();
//                        item.setCompanyNo(finalObject.getString("COMAPNYNO"));
//                        item.setSalesManNo(finalObject.getString("SALESMANNO"));
//                        item.setSalesManName(finalObject.getString("SALESMANNAME"));
//                        try {
//                            item.setIsSuspended(finalObject.getString("ISSUSPENDED"));
//                        } catch (Exception e) {
//                            Log.e("setIsSuspended", "" + e.getMessage());
//                            item.setIsSuspended(finalObject.getString("ISSUSPENDED"));
//                        }
//
//
//                        //  item.setIpAddressDevice(finalObject.getString("IpAddressDevice"));
//
//
//                        salesTeamList.add(item);
//                    }
//                    Log.e("ImportData", salesTeamList.size() + "");
//                } catch (JSONException e) {
//                    Log.e("Import Data", e.getMessage().toString());
//                }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//            } catch (MalformedURLException e) {
//                Log.e("Customer", "********ex1");
//                progressDialog.dismiss();
//                e.printStackTrace();
//            } catch (IOException e) {
//                Log.e("CustomerIOException", e.getMessage().toString());
//                progressDialog.dismiss();
////                Toast.makeText(context, "check Connection", Toast.LENGTH_SHORT).show();
//                e.printStackTrace();
//
//            } catch (JSONException e) {
//                Log.e("Customer", "********ex3  " + e.toString());
//                e.printStackTrace();
//            } catch (URISyntaxException e) {
//                e.printStackTrace();
//            } finally {
//                Log.e("Customer", "********finally");
//                progressDialog.dismiss();
//
//
//                try {
//                    if (reader != null) {
//                        reader.close();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//            }
//            return customerList;
//        }
//
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            progressDialog.dismiss();
//
//            if (result != null && result.size() != 0) {
//
//            } else {
//
//                // Toast.makeText(context, "Not able to fetch Customer data from server.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }




}
