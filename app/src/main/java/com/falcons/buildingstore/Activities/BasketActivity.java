package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.falcons.buildingstore.Adapters.VoherItemAdapter;
import com.falcons.buildingstore.R;

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
}