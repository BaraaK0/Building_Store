package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;

import com.falcons.buildingstore.Adapters.BasketItemAdapter;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;

import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BasketActivity extends BaseActivity {

    ImageButton backBtn;
    RecyclerView basketListRV;
    Button orderBtn, saveBtn;
    BottomNavigationView bottom_navigation;
    private String prevPage;


    RecyclerView BasketItem;
    RecyclerView.LayoutManager layoutManager;

    GeneralMethod generalMethod;
    AppDatabase appDatabase;
    ExportData exportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);
        init();
        fillListAdapter();

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMasterVocher(1);
                SaveDetialsVocher(1);
                generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveMasterVocher(2);
                SaveDetialsVocher(2);
                generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    int getLayoutId() {
        return R.layout.orders_basket;
    }

    void init() {
        appDatabase = AppDatabase.getInstanceDatabase(BasketActivity.this);
        exportData = new ExportData(BasketActivity.this);
        generalMethod = new GeneralMethod(BasketActivity.this);
        BasketItem = findViewById(R.id.basketListRV);
        orderBtn = findViewById(R.id.orderBtn);
        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);
    }

    void fillListAdapter() {
        layoutManager = new LinearLayoutManager(BasketActivity.this);
        BasketItem.setLayoutManager(layoutManager);
        BasketItemAdapter basketItemAdapter = new BasketItemAdapter(HomeActivity.vocher_Items, BasketActivity.this);
        BasketItem.setAdapter(basketItemAdapter);

    }

    void SaveMasterVocher(int i) {

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setIsPosted(0);
        orderMaster.setDate(generalMethod.getCurentTimeDate(1));
        orderMaster.setTime(generalMethod.getCurentTimeDate(2));
        orderMaster.setCustomerId(77);
        orderMaster.setDiscount(HomeActivity.vocher_Items.get(0).getDiscount());
        orderMaster.setTotal(CalculateTotalPrice());

        if (i == 1) orderMaster.setConfirmState(0);
        else orderMaster.setConfirmState(1);
        appDatabase.ordersMasterDao().insertOrder(orderMaster);


    }

    void SaveDetialsVocher(int x) {
        int vohno = appDatabase.ordersMasterDao().getLastVoherNo();

        for (int i = 0; i < HomeActivity.vocher_Items.size(); i++) {
            OrdersDetails ordersDetails = new OrdersDetails();
            ordersDetails.setVhfNo(vohno);
            ordersDetails.setDiscount(HomeActivity.vocher_Items.get(0).getDiscount());
            ordersDetails.setItemNo(HomeActivity.vocher_Items.get(0).getItemNCode());
            ordersDetails.setArea(HomeActivity.vocher_Items.get(0).getArea());
            ordersDetails.setPrice(HomeActivity.vocher_Items.get(0).getPrice());
            ordersDetails.setDate(generalMethod.getCurentTimeDate(1));
            ordersDetails.setTime(generalMethod.getCurentTimeDate(2));
            ordersDetails.setTax(HomeActivity.vocher_Items.get(0).getTax());

            ordersDetails.setQty(HomeActivity.vocher_Items.get(0).getQty());
            ordersDetails.setIsPosted(0);
            if (x == 1) ordersDetails.setConfirmState(0);
            else ordersDetails.setConfirmState(1);
            appDatabase.ordersDetails_dao().insertOrder(ordersDetails);
        }

        HomeActivity.vocher_Items.clear();
        fillListAdapter();
    }

    double CalculateTotalPrice() {
        double total = 0;

        for (int i = 0; i < HomeActivity.vocher_Items.size(); i++) {
            total += HomeActivity.vocher_Items.get(i).getPrice() * HomeActivity.vocher_Items.get(i).getQty();
        }
        return total;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(BasketActivity.this, HomeActivity.class);
        startActivity(intent);
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


}