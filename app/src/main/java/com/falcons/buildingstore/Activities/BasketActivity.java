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

import com.falcons.buildingstore.Adapters.VoherItemAdapter;
import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class BasketActivity extends BaseActivity {

    ImageButton backBtn;
    RecyclerView basketListRV;
    Button orderBtn, saveBtn;
    BottomNavigationView bottom_navigation;
    private String prevPage;

public class BasketActivity extends AppCompatActivity {
RecyclerView BasketItem;
RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);
init();
        VoherItemAdapter voherItemAdapter=new VoherItemAdapter( HomeActivity.vocher_Items, BasketItem.this);
        BasketItem.setAdapter(voherItemAdapter);
        layoutManager = new LinearLayoutManager(BasketItem.this);
        BasketItem.setLayoutManager(layoutManager);

    }
    void init(){
        BasketItem=findViewById(R.id.basketListRV);
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