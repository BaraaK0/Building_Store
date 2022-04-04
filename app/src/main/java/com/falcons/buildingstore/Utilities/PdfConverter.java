package com.falcons.buildingstore.Utilities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.falcons.buildingstore.Activities.OrderReport;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import com.falcons.buildingstore.BuildConfig;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;


import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;

import com.itextpdf.text.PageSize;

import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.itextpdf.text.Document;

import java.util.List;
import java.util.Locale;

import static com.itextpdf.text.Element.ALIGN_CENTER;
import com.falcons.buildingstore.R;
import com.itextpdf.text.pdf.draw.LineSeparator;

public class PdfConverter {
    private Context context;
    com.itextpdf.text.Document doc;
    File file;
    PdfWriter docWriter = null;
    //    PDFView pdfView;
    File pdfFileName;
    BaseFont base;
    GeneralMethod generalMethod;
    AppDatabase obj;

    {
        try {
            base = BaseFont.createFont("res/font/arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Font arabicFont = new Font(base, 10f);
    Font arabicFontHeader = new Font(base, 11f);
    Font arabicFontHeaderprint = new Font(base, 17f);
    Font arabicFontHeaderVochprint = new Font(base, 25f);
    public PdfConverter(Context context) {
        this.context = context;
        generalMethod=new GeneralMethod(context);
    }
    public String convertToEnglish(String value) {
        String newValue = (((((((((((value + "").replaceAll("١", "1")).replaceAll("٢", "2")).replaceAll("٣", "3")).replaceAll("٤", "4")).replaceAll("٥", "5")).replaceAll("٦", "6")).replaceAll("٧", "7")).replaceAll("٨", "8")).replaceAll("٩", "9")).replaceAll("٠", "0").replaceAll("٫", "."));
        return newValue;
    }

    public void exportListToPdf(List<?> list, String headerDate, String date, int report ) {

        PdfPTable pdfPTable= new PdfPTable(1);
        PdfPTable pdfPTableHeader= new PdfPTable(1);
        pdfPTable = getContentTable(  list,report);
        String dateTime="";
        dateTime=generalMethod.getCurentTimeDate(1);
        //dateTime=dateTime+getCurentTimeDate(2);
        pdfPTableHeader = getHeaderTable(list,report,headerDate,dateTime);

        //

        try {
//

                   doc.add(  pdfPTableHeader);
                    doc.add(pdfPTable);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
//                 doc.add(pdfPTable);

        endDocPdf();

        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && (context.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {

                Log.v("", "Permission is granted");
            } else {

                Log.v("", "Permission is revoked");
                ActivityCompat.requestPermissions(
                        (Activity) context,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                        1);
            }
        } else { // permission is automatically granted on sdk<23 upon
            // installation
            showPdf(pdfFileName);
            Log.v("", "Permission is granted");
        }

    }
    private PdfPTable getContentTable(List<?> list, int reportType) {
        PdfPTable tableContent = new PdfPTable(1);
        switch (reportType) {
            case 1:
                Log.e("createVocher",""+list.size());
                //    tableContent=
                createInvoiceForPrint((List<OrdersDetails>) list);
                Log.e("path==",String.valueOf(pdfFileName));

                PrintManager printManager=(PrintManager) context.getSystemService(Context.PRINT_SERVICE);
                try {

                    PrintDocumentAdapter printAdapter = new PdfDocumentAdapter(context, String.valueOf(pdfFileName));
                    Log.e("path2==",String.valueOf(pdfFileName));
                    printManager.print("Document", printAdapter, new PrintAttributes.Builder().build());
                    Log.e("path3==",String.valueOf(pdfFileName));
                }
                catch (Exception e)
                {
                    Log.e("Exception==",e.getMessage());
                }

                break;
        }
        return  tableContent;  }
    private void createInvoiceForPrint(List<OrdersDetails> list)
    {
        //  insertCell(pdfPTable,context.getString(R.string.serialcode      )
        //  , ALIGN_CENTER   , 1, arabicFont, BaseColor.BLACK);

                createvocherPDF("Invoice" + ".pdf", list);


    }
    void createvocherPDF(String fileName,List<OrdersDetails> list) {
        doc = new Document() ;

        docWriter = null;

        try {


            String directory_path = Environment.getExternalStorageDirectory().getPath() + "/ReportVanSales/";
            file = new File(directory_path);
            if (!file.exists()) {
                file.mkdirs();
            }
            String targetPdf = directory_path + fileName;

            File path = new File(targetPdf);

            docWriter = PdfWriter.getInstance(doc, new FileOutputStream(path));
            docWriter.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            doc.setPageSize(PageSize.A4);//size of page
            //open document
            doc.open();
            doc.add(new Chunk(""));



/////////logo
            Drawable drawable = AppCompatResources.getDrawable(context, R.drawable.logo);
            assert drawable != null;
            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapData = stream.toByteArray();



//            Bitmap imageBytes =   drawableToBitmap (context.getResources().getDrawable(R.drawable.logo));
//            imageBytes = getResizedBitmap(imageBytes, 95, 80);
//            byte[] bytes = convertBitmapToByteArray(imageBytes);


//            BitmapFactory.decodeByteArray(bytes, 0, 10);
            try {

                Image image = Image.getInstance(bitmapData);
                image.setAlignment(Element.ALIGN_CENTER);
                image.scaleAbsolute(95, 80);
                doc.add(image);

//                Image imageView = Image.getInstance(bytes);
//                imageView.setAlignment(ALIGN_CENTER);
//                //   imageView.scaleToFit(10,10);
//
//                doc.add(imageView);

            } catch (Exception e) {
            }


            ///////////////

            PdfPTable pdfPTable0 = new PdfPTable(4);
            pdfPTable0.setWidthPercentage(100f);
            pdfPTable0.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            insertCell(pdfPTable0, context.getString(R.string.vocher) , ALIGN_CENTER, 7, arabicFontHeaderprint, BaseColor.BLACK);
            insertCell(pdfPTable0, "ARTIC" , ALIGN_CENTER, 7, arabicFontHeaderprint, BaseColor.BLACK);
            insertCell(pdfPTable0, "5666666" , ALIGN_CENTER, 7, arabicFontHeaderprint, BaseColor.BLACK);

            PdfPTable pdfPTable1 = new PdfPTable(4);
            pdfPTable1.setWidthPercentage(100f);

            String dateTime="";
            dateTime=generalMethod.getCurentTimeDate(1);
            insertCell(pdfPTable1, context.getString(R.string.date) + " : " + dateTime, Element.ALIGN_BASELINE, 7, arabicFontHeader, BaseColor.BLACK);
            insertCell(pdfPTable1, context.getString(R.string.VoHNO) + " : " + list.get(0).getVhfNo(), Element.ALIGN_BASELINE, 7, arabicFontHeader, BaseColor.BLACK);
            insertCell(pdfPTable1, context.getString(R.string.customerName) + " : " + OrderReport.Cusname, Element.ALIGN_BASELINE, 7, arabicFontHeader, BaseColor.BLACK);


            doc.add(pdfPTable0);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));
            doc.add(pdfPTable1);
            doc.add(new Paragraph("\n"));
            doc.add(new Paragraph("\n"));

            PdfPTable pdfPTable = new PdfPTable(4);
            pdfPTable.setWidthPercentage(100f);
            pdfPTable.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);





            insertCell(pdfPTable,context.getResources().getString(R.string.item_Name_   )   , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable,context.getResources().getString(R.string.qty) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable,context.getResources().getString(R.string.price_) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
            insertCell(pdfPTable,context.getResources().getString(R.string.total) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

            doc.add(  pdfPTable);
            doc.add(new LineSeparator());

            //////////////////
            PdfPTable pdfPTable3 = new PdfPTable(4);
            pdfPTable3.setWidthPercentage(100f);
            pdfPTable3.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            for (int i = 0; i < list.size(); i++) {



                {
                      insertCell(pdfPTable3, String.valueOf(list.get(i).getItemName() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
                    insertCell(pdfPTable3, String.valueOf(list.get(i).getQty() ) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);
                    insertCell(pdfPTable3, String.valueOf(list.get(i).getPrice()) , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                    String amount = "" + (list.get(i).getQty() * list.get(i).getPrice() - list.get(i).getDiscount());
                    Log.e("amount==",amount+"");
                    insertCell(pdfPTable3, amount      , ALIGN_CENTER, 1, arabicFont, BaseColor.BLACK);

                }
            }

            //   doc.add(new Paragraph(list.get(0).getItemName(),arabicFont));
            doc.add(  pdfPTable3);
            doc.add(new com.itextpdf.text.Paragraph("\n"));
            doc.add(new com.itextpdf.text.Paragraph("\n"));
            doc.add(new Paragraph("\n"));


            PdfPTable table2 = new PdfPTable(1);
            table2.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            PdfPCell cell7 = new PdfPCell(new Paragraph(context.getString(R.string.net_sales) + " : " +OrderReport.netsal,arabicFontHeaderprint));
            PdfPCell cell8 = new PdfPCell(new Paragraph(context.getString(R.string.Sub_total) + " : " +OrderReport.subtotal,arabicFontHeaderprint));
             PdfPCell cell10 = new PdfPCell(new Paragraph("استلمت البضاعة كاملة و بحالة جيدة وخالية من أية عيوب وأتعهد بدفع قيمة هذه الفاتورة.",arabicFontHeaderprint));
            PdfPCell cell11 = new PdfPCell(new Paragraph("المستلم : ", arabicFontHeaderprint));
            PdfPCell cell12 = new PdfPCell(new Paragraph("التوقيع : ",arabicFontHeaderprint));

            cell10.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell11.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
            cell12.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

            cell10.setBorder(Rectangle.NO_BORDER);
            cell11.setBorder(Rectangle.NO_BORDER);
            cell12.setBorder(Rectangle.NO_BORDER);
            cell7.setBorder(Rectangle.NO_BORDER);
            cell8.setBorder(Rectangle.NO_BORDER);
            table2.addCell(cell7);
            table2.addCell(cell8);
            table2.addCell(cell10);
            table2.addCell(cell11);
            table2.addCell(cell12);

            table2.setTotalWidth(doc.right(doc.rightMargin())
                    - doc.left(doc.leftMargin()));

            table2.writeSelectedRows(0, -1,
                    doc.left(doc.leftMargin()),
                    table2.getTotalHeight() + doc.bottom(doc.bottomMargin()),
                    docWriter.getDirectContent());

//            doc.add(table2);

            Log.e("path44", "" + targetPdf);
            pdfFileName = path;
            Log.e("pdfFileName", "" + pdfFileName);

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    private PdfPTable getHeaderTable(List<?> list,int reportType ,String headerDate, String date) {
        PdfPTable pdfPTableHeader = new PdfPTable(7);
        pdfPTableHeader.setWidthPercentage(100f);
        pdfPTableHeader.setSpacingAfter(20);
        pdfPTableHeader.setRunDirection(com.itextpdf.text.pdf.PdfWriter.RUN_DIRECTION_RTL);
  //      insertCell(pdfPTableHeader, headerDate, ALIGN_CENTER, 7, arabicFontHeader, BaseColor.BLACK);
        if(reportType!=1)   insertCell(pdfPTableHeader, context.getString(R.string.date) + " : " + date, Element.ALIGN_LEFT, 7, arabicFontHeader, BaseColor.BLACK);



        return  pdfPTableHeader;
    }
    void showPdf(File path) {
        Log.e("showPdf",""+path);

        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(uri, "application/pdf");//intent.setDataAndType(Uri.fromFile(path), "application/pdf");
            context.startActivity(intent);
        }
        catch (Exception e)
        {
            Toast.makeText(context, "Not able to open flder", Toast.LENGTH_SHORT).show();
        }


    }
    void endDocPdf() {

        if (doc != null) {
            //close the document
            doc.close();
        }
        if (docWriter != null) {
            //close the writer
            docWriter.close();
        }
    }
    public void insertCell(PdfPTable table, String text, int align, int colspan, Font font, BaseColor border) {

        //create a new cell with the specified Text and Font
        PdfPCell cell = new PdfPCell(new Phrase(text, font));
        //set the cell alignment
        cell.setHorizontalAlignment(align);
        //set the cell column span in case you want to merge two or more cells
        cell.setColspan(colspan);
        cell.setBorderColor(border);
        //in case there is no text and you wan to create an empty row
        if (text.trim().equalsIgnoreCase("")) {
            cell.setMinimumHeight(10f);
        }

        if (Locale.getDefault().getLanguage().equals("ar"))
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
        else
            cell.setRunDirection(PdfWriter.RUN_DIRECTION_LTR);
//        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        //add the call to the table
        cell.setBorder(Rectangle.NO_BORDER);
        table.addCell(cell);

    }
    public static Bitmap drawableToBitmap (Drawable drawable) {
        Bitmap bitmap = null;

        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            if(bitmapDrawable.getBitmap() != null) {
                return bitmapDrawable.getBitmap();
            }
        }

        if(drawable.getIntrinsicWidth() <= 0 || drawable.getIntrinsicHeight() <= 0) {
            bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888); // Single color bitmap will be created of 1x1 pixel
        } else {
            bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }
    public byte[] convertBitmapToByteArray(Bitmap bitmap) {

        ByteArrayOutputStream stream = null;
        try {
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

            return stream.toByteArray();
        }finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                   // Log.e(Helper.class.getSimpleName(), "ByteArrayOutputStream was not closed");
                }
            }
        }
    }
}
