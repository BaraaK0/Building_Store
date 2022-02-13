package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.falcons.buildingstore.R;

public class BasketActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);
    }
}