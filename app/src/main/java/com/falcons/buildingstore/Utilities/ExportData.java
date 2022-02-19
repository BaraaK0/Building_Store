package com.falcons.buildingstore.Utilities;

import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;

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
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ExportData {

    List<OrderMaster> vouchers;
    private Context context;
    List<OrdersDetails> items;
    private AppDatabase appDatabase;
    private String URL_TO_HIT, ipAddress, ipWithPort, headerDll, CONO = "";
    private ProgressDialog progressDialog, pdValidation;
    SweetAlertDialog progressSave, pdVoucher;
    AppDatabase mHandler;
    JSONObject vouchersObject;
    private JSONArray jsonArrayVouchers, jsonArrayItems;

    public ExportData(Context context) {
        this.context = context;
        this.mHandler = AppDatabase.getInstanceDatabase(context);

        ipAddress = IP_PREF;
        ipWithPort = PORT_PREF;
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
            String link = "http://" + ipAddress.trim() + ":" + ipWithPort.trim() + headerDll.trim() + "/ExportSALES_VOUCHER_D";
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
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
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

                    URL_TO_HIT = "http://" + ipAddress.trim() + ":" + ipWithPort.trim() + headerDll.trim() + "/ExportSALES_VOUCHER_M";


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
                nameValuePairs.add(new BasicNameValuePair("CONO", CONO.trim()));
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
//    private class JSONTaskSaveVouchers extends AsyncTask<String, String, String> {
//        private String JsonResponse = null;
//        private HttpURLConnection urlConnection = null;
//        private BufferedReader reader = null;
//        //        SweetAlertDialog pdItem=null;
//        public  String salesNo="",finalJson;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressSave = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
//            progressSave.getProgressHelper().setBarColor(Color.parseColor("#FDD835"));
//            progressSave.setTitleText(" Save Vouchers");
//            progressSave.setCancelable(false);
//            progressSave.show();
//
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
////            URLConnection connection = null;
////            BufferedReader reader = null;
////
////            try {
////
////                try {
////                    if (!ipAddress.equals("")) {
//            // URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/Falcons/VAN.dll/GetVanAllData?STRNO="+salesNo+"&CONO="+CONO;
//            // http://localhost:8082/SaveVouchers?CONO=290&STRNO=5
//
//            //URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/SaveVouchers?CONO="+CONO+"&STRNO="+SalesManLogin;
//
//            try {
//                //  URL_TO_HIT = "http://"+ipAddress.trim()+":" + ipWithPort.trim() +"/ExportSALES_VOUCHER_D?CONO="+CONO.trim()+"&JSONSTR="+vouchersObject.toString().trim();
//
////LINK : http://localhost:8082/ExportITEMSERIALS?CONO=290&JSONSTR={"JSN":[{"VHFNO":"123","STORENO":"5","TRNSDATE":"01/01/2021","TRANSKIND":"1","ITEMNO":"321","SERIAL_CODE":"369258147852211","QTY":"1","VSERIAL":"1","ISPOSTED":"0"}]}
//                String link = "http://"+ipAddress.trim()+":" + ipWithPort.trim() + headerDll.trim()+"/SaveVouchers";
//                // Log.e("ipAdress", "ip -->" + ip);
//            String data = "CONO="+CONO.trim()+"&STRNO=" +SalesManLogin+"&VHFTYPE="+taxType;
//                Log.e("tag_link", "ExportData -->" + link);
//                Log.e("tag_data", "ExportData -->" + data);
//
//////
//                URL url = new URL(link);
//
//                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//                httpURLConnection.setDoOutput(true);
//                httpURLConnection.setDoInput(true);
//                httpURLConnection.setRequestMethod("POST");
//
//
//
//                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
//                wr.writeBytes(data);
//                wr.flush();
//                wr.close();
//
//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//
//                StringBuffer stringBuffer = new StringBuffer();
//
//                while ((JsonResponse = bufferedReader.readLine()) != null) {
//                    stringBuffer.append(JsonResponse + "\n");
//                }
//
//                bufferedReader.close();
//                inputStream.close();
//                httpURLConnection.disconnect();
//
//                Log.e("tag", "ExportData -->" + stringBuffer.toString());
//
//                return stringBuffer.toString();
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (urlConnection != null) {
//                    urlConnection.disconnect();
//                }
//                if (reader != null) {
//                    try {
//                        reader.close();
//                    } catch (final IOException e) {
//                        Log.e("tag", "Error closing stream", e);
//                    }
//                }
//            }
//            return null;
//
//        }
//
//
//        @Override
//        protected void onPostExecute(final String result) {
//            super.onPostExecute(result);
//            progressSave.setTitle("Saved Vouchers");
//            Log.e("onPostExecute","---15----"+result);
//
//            if (result != null && !result.equals("")) {
//
//
//            } else {
//                progressSave.dismissWithAnimation();
//
//            }
//
//        }
//    }


}