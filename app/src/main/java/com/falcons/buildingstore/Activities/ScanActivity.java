package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.HomeActivity.searchItemsEdt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanActivity extends AppCompatActivity
        implements ZXingScannerView.ResultHandler{

    private ZXingScannerView mScannerView;
    private  String type="";

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        type = getIntent().getStringExtra("key");

        mScannerView = new ZXingScannerView(this);
        List<BarcodeFormat> formats = new ArrayList<>();
        formats.add(BarcodeFormat.CODABAR);
        formats.add(BarcodeFormat.CODE_128);
        formats.add(BarcodeFormat.MAXICODE);
        formats.add(BarcodeFormat.CODE_93);
        formats.add(BarcodeFormat.CODE_39);
//            formats.add(BarcodeFormat.QR_CODE);

        setContentView(mScannerView);// Programmatically initialize the scanner view
//            setContentView(mScannerView);                // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void handleResult(Result rawResult) {

        if(type.equals("1"))
        {
            searchItemsEdt.setText(rawResult.getText().trim());
            Log.e("Code_Scanned","" + rawResult.getText().trim());
        }

        onBackPressed();

        // If you would like to resume scanning, call this method below:
        //mScannerView.resumeCameraPreview(this);
    }
}