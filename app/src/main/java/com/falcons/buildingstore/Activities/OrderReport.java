package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.falcons.buildingstore.Adapters.OrderReportAdapter;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Utilities.PdfConverter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.firebase.inappmessaging.model.ImageData;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfWriter;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OrderReport extends AppCompatActivity {
    OrderReportAdapter orderReportAdapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView ordersDetalisRec;
    List<OrdersDetails> ordersDetails = new ArrayList<>();
    AppDatabase appDatabase;
    TextView ORDERNO, Cus_name, date, total,tax,netsales;
    Button Rep_order, print_order;
    public static String Cusname;
  ExportData exportData;
    Bundle bundle ;
    int VohNu;
    public ArrayList<Item> order_Items = new ArrayList<>();
    GeneralMethod generalMethod;
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    BottomNavigationView bottom_navigation;
    public static  int Report_VOHNO=0;
    public static  String sendCus_name="";
    public static double netsal,taxx,subtotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);
        try {
            layoutManager = new LinearLayoutManager(OrderReport.this);
            init();
            bottom_navigation.setSelectedItemId(R.id.action_cart);

            bottom_navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {

                        case R.id.action_home:

                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.action_cart:

                            return true;

                        case R.id.exportdata:

                            exportData.exportSalesVoucherM();
                            return true;

                        case R.id.action_report:

                            startActivity(new Intent(getApplicationContext(), ShowPreviousOrder.class));
                            overridePendingTransition(0, 0);
                            return true;

                        case R.id.action_add:

                            final Dialog dialog = new Dialog(OrderReport.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.adddailog);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                            lp.copyFrom(dialog.getWindow().getAttributes());
                            lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
                            lp.gravity = Gravity.CENTER;
                            dialog.getWindow().setAttributes(lp);
                            dialog.show();

                            UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();

                            int userType = appDatabase.usersDao().getUserType(userLogs.getUserName());
                            if (userType == 0)
                                dialog.findViewById(R.id.adduser).setVisibility(View.GONE);
                            else
                                dialog.findViewById(R.id.adduser).setVisibility(View.VISIBLE);

                            dialog.findViewById(R.id.addCustomer).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), AddNewCustomer.class));
                                    overridePendingTransition(0, 0);
                                    dialog.dismiss();
                                }
                            });
                            dialog.findViewById(R.id.adduser).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), AddNewUser.class));
                                    overridePendingTransition(0, 0);
                                    dialog.dismiss();
                                }
                            });

                            return true;
                    }
                    return false;
                }
            });
            Log.e("ordersDetails==", ordersDetails.size() + "");
            ordersDetails = appDatabase.ordersDetails_dao().getAllOrdersByNumber(VohNu);
            //    ordersDetails=appDatabase.ordersDetails_dao().getAllOrders();
            Log.e("ordersDetails==", ordersDetails.size() + "");
            ordersDetalisRec.setLayoutManager(layoutManager);
            ORDERNO.setText(ordersDetails.get(0).getVhfNo() + "");


            Cusname = appDatabase.customersDao().getCustmByNumber(ordersDetails.get(0).getCustomerId());
            Cus_name.setText(Cusname);
            Log.e("Cus_id==", ordersDetails.get(0).getCustomerId() + "");


            date.setText(ordersDetails.get(0).getDate());
            total.setText(ordersDetails.get(0).getTotal() + "");
            fillAdapter();
            if (ordersDetails.get(0).getConfirmState() != 0)
                Rep_order.setVisibility(View.INVISIBLE);
            else
                Rep_order.setVisibility(View.VISIBLE);
            ///order btn
            Rep_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    Log.e("Rep_order", "Rep_order");

                    //1. fill Items List with qty and discount based on ordersDetails List
                    HomeActivity.vocher_Items.clear();

                    for (int i = 0; i < ordersDetails.size(); i++) {
                        for (int j = 0; j < HomeActivity.allItemList_rv.size(); j++)
                            if (ordersDetails.get(i).getItemNo().equals(HomeActivity.allItemList_rv.get(j).getItemNCode())) {
                                HomeActivity.allItemList_rv.get(j).setDiscount(ordersDetails.get(i).getDiscount());
                                HomeActivity.allItemList_rv.get(j).setQty(ordersDetails.get(i).getQty());
                                HomeActivity.allItemList_rv.get(j).setArea(ordersDetails.get(i).getArea());
                                HomeActivity.vocher_Items.add(HomeActivity.allItemList_rv.get(j));


                            } }

                    //spinner customer will be set based on ordersDetails List
                    //{}


                            Report_VOHNO=ordersDetails.get(0).getVhfNo();

                    Intent intent = new Intent(OrderReport.this, BasketActivity.class);
                /*    bundle = new Bundle();

                    bundle.putInt("Report_VOHNO", ordersDetails.get(0).getVhfNo());
                    //  intent.putExtras(bundle);
                    intent.putExtras(bundle);*/
                    startActivity(intent);
                ;
                    //

                }
            });

            print_order.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (checkPermission()) {
                        exportToPdf();
                    } else {
                        requestPermission();
                    }

                }
            });

        } catch (Exception exception) {
            Log.e("exception==", exception.getMessage());
        }
    }

    void fillAdapter() {
        Log.e("ordersDetails==", ordersDetails.size() + "");
        orderReportAdapter = new OrderReportAdapter(OrderReport.this, ordersDetails);
        ordersDetalisRec.setAdapter(orderReportAdapter);
    }

    void init() {
        ordersDetails.clear();
        tax= findViewById(R.id.tax);
       netsales= findViewById(R.id.netsales);


        bottom_navigation = findViewById(R.id.bottom_navigation);
        exportData=new ExportData(OrderReport.this);
        generalMethod = new GeneralMethod(OrderReport.this);
        Rep_order = findViewById(R.id.Rep_order);
        print_order = findViewById(R.id.print_order);
      try {
          Bundle bundle = getIntent().getExtras();
          VohNu = bundle.getInt("VOHNO");
       netsal = bundle.getDouble("netsale");
         taxx = bundle.getDouble("tax");
          subtotal= bundle.getDouble("subtotal");
          Log.e("VOHNO==", VohNu + "");

          tax.setText(taxx+"");
          netsales.setText(netsal+"");

      }catch (Exception exception){
          Log.e("exception==", exception.getMessage() + "");
      }

        appDatabase = AppDatabase.getInstanceDatabase(OrderReport.this);
        ordersDetalisRec = findViewById(R.id.ordersDetalisRec);
        total = findViewById(R.id.total);
        ORDERNO = findViewById(R.id.ORDERNO);
        Cus_name = findViewById(R.id.Cus_name);
        date = findViewById(R.id.date);
        Log.e("VOHNO==", VohNu + "");
        appDatabase = AppDatabase.getInstanceDatabase(OrderReport.this);
        ordersDetalisRec = findViewById(R.id.ordersDetalisRec);
        total = findViewById(R.id.total);
        ORDERNO = findViewById(R.id.ORDERNO);
        Cus_name = findViewById(R.id.Cus_name);
        date = findViewById(R.id.date);


    }

    double CalculateTotal() {
        double total = 0;
        for (int i = 0; i < ordersDetails.size(); i++)
            total += ordersDetails.get(i).getPrice();
        return total;
    }

    public void exportToPdf() {


        PdfConverter pdf = new PdfConverter(OrderReport.this);
        pdf.exportListToPdf(ordersDetails, "Voucher", "", 1);


    }

    public void createPDF() {
//     //   Document
//        com.itextpdf.layout.Document doc = new com.itextpdf.layout.Document();
//
//        try {
//            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PDF";
//
//            File dir = new File(path);
//            if(!dir.exists())
//                dir.mkdirs();
//
//            Log.d("PDFCreator", "PDF Path: " + path);
//
//            File file = new File(dir, "demo.pdf");
//            FileOutputStream fOut = new FileOutputStream(file);
//
//            PdfWriter.getInstance(doc, fOut);
//
//            //open the document
//            doc.open();
//
//            /* Create Paragraph and S`enter code here`et Font */
//            Paragraph p1 = new Paragraph("Hi! I am Generating my first PDF using DroidText");
//
//            /* Create Set Font and its Size */
//            Font paraFont= new Font(Font.HELVETICA);
//            paraFont.setSize(16);
//            p1.setAlignment(Paragraph.ALIGN_CENTER);
//            p1.setFont(paraFont);
//
//            //add paragraph to document
//            doc.add(p1);
//
//
//            Paragraph p2 = new Paragraph("This is an example of a simple paragraph");
//
//            /* You can also SET FONT and SIZE like this */
//            Font paraFont2= new Font(Font.COURIER,14.0f, Color.GREEN);
//            p2.setAlignment(Paragraph.ALIGN_CENTER);
//            p2.setFont(paraFont2);
//
//            doc.add(p2);
//
//            /* Inserting Image in PDF */
//            /*ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            Bitmap bitmap = BitmapFactory.decodeResource(getBaseContext().getResources(), R.drawable.android);
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100 , stream);
//            Image myImg = Image.getInstance(stream.toByteArray());
//            myImg.setAlignment(Image.MIDDLE);
//
//            //add image to document
//            doc.add(myImg);*/
//
//            //set footer
//            Phrase footerText = new Phrase("This is an example of a footer");
//            HeaderFooter pdfFooter = new HeaderFooter(footerText, false);
//            doc.setFooter(pdfFooter);
//
//            Toast.makeText(getApplicationContext(), "Created...", Toast.LENGTH_LONG).show();
//
//        } catch (DocumentException de) {
//            Log.e("PDFCreator", "DocumentException:" + de);
//        } catch (IOException e) {
//            Log.e("PDFCreator", "ioException:" + e);
//        }
//        finally
//        {
//            doc.close();
//        }
    }




    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                    exportToPdf();
                } else {
                    Toast.makeText(this, "Permission Denied.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
    @Override
    public void onBackPressed() {

    }
}