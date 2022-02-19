package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BasketActivity extends BaseActivity {

    ImageButton backBtn;
    RecyclerView basketListRV;
    Button orderBtn, saveBtn;
    BottomNavigationView bottom_navigation;
    private String prevPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);

        init();

//        bottom_navigation.setSelectedItemId(R.id.action_cart);
//
//        Intent intent = getIntent();
//        prevPage = intent.getStringExtra("previous");

        backBtn.setOnClickListener(v -> {

            onBackPressed();

        });

//        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                switch (item.getItemId()) {
//
//                    case R.id.action_home:
//                        Intent intent = new Intent(BasketActivity.this, HomeActivity.class);
//                        startActivity(intent);
//                        return true;
//
//                    case R.id.action_cart:
//                        return true;
//
//                    case R.id.action_add:
//                        return true;
//
//                    case R.id.action_report:
//                        return true;
//
//                }
//                return false;
//
//            }
//        });

    }

    @Override
    int getLayoutId() {
        return R.layout.orders_basket;
    }

    @Override
    int getBottomNavigationMenuItemId() {
        return R.id.action_cart;
    }

//    @Override
//    public void onBackPressed() {
//
//        finish();
//
//    }

    private void init() {

        backBtn = findViewById(R.id.backBtn);
        basketListRV = findViewById(R.id.basketListRV);
        orderBtn = findViewById(R.id.orderBtn);
        saveBtn = findViewById(R.id.saveBtn);
        bottom_navigation = findViewById(R.id.bottom_navigation);

    }

}