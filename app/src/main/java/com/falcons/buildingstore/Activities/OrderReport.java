package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.falcons.buildingstore.Adapters.OrderReportAdapter;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;
import com.falcons.buildingstore.R;

import java.util.ArrayList;
import java.util.List;

public class OrderReport extends AppCompatActivity {
    OrderReportAdapter orderReportAdapter;
    RecyclerView.LayoutManager layoutManager;
   RecyclerView ordersDetalisRec;
    List<OrdersDetails> ordersDetails=new ArrayList<>();
    AppDatabase appDatabase;
    TextView ORDERNO,Cus_name,date,total;
   Button Rep_order;
 int VohNu;
    public ArrayList<Item> order_Items = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_report);
        layoutManager = new LinearLayoutManager(OrderReport.this);
        init();
        ordersDetails=appDatabase.ordersDetails_dao().getAllOrdersByNumber(VohNu);
        ordersDetalisRec.setLayoutManager(layoutManager);
        ORDERNO.setText(ordersDetails.get(0).getVhfNo()+"");

     try {
         String Cusname   =appDatabase.customersDao().getCustmByNumber(ordersDetails.get(0).getCustomerId());
         Cus_name.setText(Cusname);
     }
     catch (Exception  exception){

     }

        date.setText(ordersDetails.get(0).getDate());
        total.setText(ordersDetails.get(0).getTotal()+"");
        fillAdapter();


        ///order btn
        Rep_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           try {

               //1. fill Items List with qty and discount based on ordersDetails List
               HomeActivity.vocher_Items.clear();
              for (int i=0;i<ordersDetails.size();i++)
              {
                  for (int j=0;j<HomeActivity.itemList.size();j++)
                  if(ordersDetails.get(i).getItemNo().equals( HomeActivity.itemList.get(j).getItemNCode()))
                  HomeActivity.itemList.get(i).setDiscount(ordersDetails.get(i).getDiscount());
                  HomeActivity.itemList.get(i).setQty(ordersDetails.get(i).getQty());
                  HomeActivity.vocher_Items.add(  HomeActivity.itemList.get(i));
              }

             //spinner customer will be set based on ordersDetails List
               //{}

               Bundle bundle = new Bundle();

               bundle.putInt("Report_VOHNO", ordersDetails.get(0).getVhfNo());

               Intent intent = new Intent(OrderReport.this, BasketActivity.class);
             //  intent.putExtras(bundle);
               startActivity(intent);
           }catch (Exception exception){
               Log.e("BasketActivityexception",exception.getMessage());
           }
            }
        });




    }
    void fillAdapter(){
        Log.e("ordersDetails==",ordersDetails.size()+"");
        orderReportAdapter=new OrderReportAdapter(OrderReport.this,ordersDetails);
        ordersDetalisRec.setAdapter(orderReportAdapter);
    }
    void    init() {
        Rep_order=findViewById(R.id.  Rep_order);
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
}