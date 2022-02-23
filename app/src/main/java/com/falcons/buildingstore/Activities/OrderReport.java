package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.falcons.buildingstore.Adapters.OrderReportAdapter;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Utilities.PdfConverter;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class OrderReport extends AppCompatActivity {
    OrderReportAdapter orderReportAdapter;
    RecyclerView.LayoutManager layoutManager;
   RecyclerView ordersDetalisRec;
    List<OrdersDetails> ordersDetails=new ArrayList<>();
    AppDatabase appDatabase;
    TextView ORDERNO,Cus_name,date,total;
   Button Rep_order,print_order;
    public  static String Cusname;
 int VohNu;
    public ArrayList<Item> order_Items = new ArrayList<>();
    GeneralMethod generalMethod;
    // constant code for runtime permissions
    private static final int PERMISSION_REQUEST_CODE = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);
        try {
        layoutManager = new LinearLayoutManager(OrderReport.this);
        init();
            Log.e("ordersDetails==",ordersDetails.size()+"");
       ordersDetails=appDatabase.ordersDetails_dao().getAllOrdersByNumber(VohNu);
        //    ordersDetails=appDatabase.ordersDetails_dao().getAllOrders();
            Log.e("ordersDetails==",ordersDetails.size()+"");
        ordersDetalisRec.setLayoutManager(layoutManager);
        ORDERNO.setText(ordersDetails.get(0).getVhfNo()+"");


       Cusname   =appDatabase.customersDao().getCustmByNumber(ordersDetails.get(0).getCustomerId());
         Cus_name.setText(Cusname);
         Log.e("Cus_id==", ordersDetails.get(0).getCustomerId()+"");



        date.setText(ordersDetails.get(0).getDate());
        total.setText(ordersDetails.get(0).getTotal()+"");
        fillAdapter();
        if(ordersDetails.get(0).getConfirmState()!=0)
        Rep_order.setVisibility(View.INVISIBLE);
        else
            Rep_order.setVisibility(View.VISIBLE);
        ///order btn
        Rep_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


               Log.e("Rep_order", "Rep_order");


                 /*  if (Build.VERSION.SDK_INT >= 23) {
                       if (OrderReport.this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                               && (OrderReport.this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
                           try {
                               createPdf2();
                           } catch (FileNotFoundException e) {
                               Log.e("FileNotFoundException",  e.getMessage());
                           }
                           Log.e("", "Permission is granted");
                       } else {

                           Log.e("", "Permission is revoked");
                           ActivityCompat.requestPermissions(
                                   OrderReport.this,
                                   new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                                   1);
                       }
                   } else { // permission is automatically granted on sdk<23 upon
                       // installation
                       try {
                           createPdf();
                       } catch (FileNotFoundException e) {

                           Log.e("FileNotFoundException",  e.getMessage());
                       }
                       Log.e("", "Permission is granted");
                   }*/




               ////////


               //1. fill Items List with qty and discount based on ordersDetails List
               HomeActivity.vocher_Items.clear();

              for (int i=0;i<ordersDetails.size();i++)
              {
                  for (int j=0;j<HomeActivity.allItemList_rv.size();j++)
                  if(ordersDetails.get(i).getItemNo().equals( HomeActivity.allItemList_rv.get(j).getItemNCode()))
                  HomeActivity.allItemList_rv.get(i).setDiscount(ordersDetails.get(i).getDiscount());
                  HomeActivity.allItemList_rv.get(i).setQty(ordersDetails.get(i).getQty());
                  HomeActivity.vocher_Items.add(  HomeActivity.allItemList_rv.get(i));
              }

             //spinner customer will be set based on ordersDetails List
               //{}

               Bundle bundle = new Bundle();

               bundle.putInt("Report_VOHNO", ordersDetails.get(0).getVhfNo());

               Intent intent = new Intent(OrderReport.this, BasketActivity.class);
             //  intent.putExtras(bundle);
               startActivity(intent);

            }
        });

        print_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkPermission()) {
                    exportToPdf();
                }
            }
        });

        }
        catch (Exception  exception){
            Log.e("exception==",exception.getMessage());
        }
    }
    void fillAdapter(){
        Log.e("ordersDetails==",ordersDetails.size()+"");
        orderReportAdapter=new OrderReportAdapter(OrderReport.this,ordersDetails);
        ordersDetalisRec.setAdapter(orderReportAdapter);
    }
    void    init() {
        ordersDetails.clear();
        generalMethod=new GeneralMethod(OrderReport.this);
        Rep_order=findViewById(R.id.  Rep_order);
        print_order=findViewById(R.id.  print_order);
        Bundle bundle = getIntent().getExtras();
        VohNu = bundle.getInt("VOHNO");
        Log.e("VOHNO==",VohNu+"");
        appDatabase=AppDatabase.getInstanceDatabase(OrderReport.this);
        ordersDetalisRec=findViewById(R.id.   ordersDetalisRec);
        total=findViewById(R.id.   total);
        ORDERNO =findViewById(R.id.   ORDERNO);
                Cus_name=findViewById(R.id.   Cus_name);
                date=findViewById(R.id.   date);


    }
  double CalculateTotal(){
        double total=0;
        for (int i=0;i<ordersDetails.size();i++)
            total+=   ordersDetails.get(i).getPrice();
        return total;
  }

    public  void exportToPdf( ){


        PdfConverter pdf =new PdfConverter(OrderReport.this);
        pdf.exportListToPdf(    ordersDetails,"Vocher","",1);


    }
    public void createPDF()
    {
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


    private void createPdf() throws FileNotFoundException {
        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).toString();
        File file = new File(pdfPath, "Order_Report.pdf");
        Log.e("createPdf",  "createPdf");
        OutputStream outputStream = new FileOutputStream(file);
        PdfWriter pdfWriter = new PdfWriter(file);
        Log.e("pdfWriter",  "pdfWriter"+pdfWriter.getOutputStream());
        PdfDocument pdfDocument = new PdfDocument(pdfWriter);

        Document document = new Document(pdfDocument);

        Drawable drawable = AppCompatResources.getDrawable(this, R.drawable.logo);

        assert drawable != null;
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] bitmapData = stream.toByteArray();

        ImageData imageData = ImageDataFactory.create(bitmapData);
        Image image = new Image(imageData);
        image.setHeight(100);
        image.setWidth(100);

//
        Paragraph paragraph1 = new Paragraph("Transfers Report").setBold();
       Paragraph paragraph2 = new Paragraph("Date: " +generalMethod.getCurentTimeDate(1));

      document.add(image)
               .add(paragraph1)
               .add(paragraph2);
//                .add(paragraph3)
//                .add(table);
//
        document.close();
//
        Toast.makeText(this, "PDF Created", Toast.LENGTH_LONG).show();
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
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {

                // after requesting permissions we are showing
                // users a toast message of permission granted.
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                if (writeStorage && readStorage) {
                    Toast.makeText(this, "Permission Granted..", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission Denined.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }
}