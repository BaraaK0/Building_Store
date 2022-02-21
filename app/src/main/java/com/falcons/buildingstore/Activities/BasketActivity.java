package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.View;
import android.widget.Button;

import com.falcons.buildingstore.Adapters.BasketItemAdapter;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;

import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class BasketActivity extends AppCompatActivity {



    ImageButton backBtn;
    Button orderBtn, saveBtn;
    BottomNavigationView bottom_navigation;

    RecyclerView BasketItem;
    RecyclerView.LayoutManager layoutManager;

    GeneralMethod generalMethod;
    AppDatabase appDatabase;
    ExportData exportData;
    double   itemTax=0,itemTotal=0,subTotal=0,itemTotalAfterTax=0,netTotal=0;
    private double itemTotalPerc=0,itemDiscVal=0,totalDiscount=0,totalTaxValue=0;

    private TextInputLayout customer_textInput;
    private AutoCompleteTextView customerTv;
    private List<CustomerInfo> allCustomers;
    private ArrayList<String> customerNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);
        init();
        fillListAdapter();

        /*  Initialize Customers  */
        allCustomers = appDatabase.customersDao().getAllCustms();
        customerNames = new ArrayList<>();

        for (int i = 0; i < allCustomers.size(); i++) {

            customerNames.add(allCustomers.get(i).getCustomerName());

        }

        ArrayAdapter<String> customersAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, customerNames);

        customerTv.setAdapter(customersAdapter);

        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(HomeActivity.vocher_Items.size()!=0) {
                    SaveMasterVocher(1);
                    SaveDetialsVocher(1);
                    generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                }
                else
                {
                    generalMethod.showSweetDialog(BasketActivity.this, 3, getResources().getString(R.string.fillbasket), "");
                String selectedCustomer = customerTv.getText().toString().trim();
                if (customerNames.contains(selectedCustomer) && !selectedCustomer.equals("")) {

                    customer_textInput.setError(null);
                    SaveMasterVocher(1);
                    SaveDetialsVocher(1);
                    generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                } else {

                    customer_textInput.setError("*");

                }

                }
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (HomeActivity.vocher_Items.size() != 0) {
                    CalculateTax(1, HomeActivity.vocher_Items);
                    SaveMasterVocher(2);
                    SaveDetialsVocher(2);
                    generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                } else {
                    generalMethod.showSweetDialog(BasketActivity.this, 3, getResources().getString(R.string.fillbasket), "");
                    String selectedCustomer = customerTv.getText().toString().trim();
                    if (customerNames.contains(selectedCustomer) && !selectedCustomer.equals("")) {

                        SaveMasterVocher(2);
                        SaveDetialsVocher(2);
                        generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");

                    } else {

                        customer_textInput.setError("*");

                    }

                }
            }   });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    void init() {

        appDatabase = AppDatabase.getInstanceDatabase(BasketActivity.this);
        exportData = new ExportData(BasketActivity.this);
        generalMethod = new GeneralMethod(BasketActivity.this);
        BasketItem = findViewById(R.id.basketListRV);
        orderBtn = findViewById(R.id.orderBtn);
        saveBtn = findViewById(R.id.saveBtn);
        backBtn = findViewById(R.id.backBtn);
        customer_textInput = findViewById(R.id.customer_textInput);
        customerTv = findViewById(R.id.customerTv);
        bottom_navigation = findViewById(R.id.bottom_navigation);
        allCustomers = new ArrayList<>();

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

                        final Dialog dialog = new Dialog(BasketActivity.this);
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
       // orderMaster.setCustomerId();
        orderMaster.setDiscount(HomeActivity.vocher_Items.get(0).getDiscount());
        UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();
        orderMaster.setUserNo(userLogs.getUserID());
        orderMaster.setNetTotal(netTotal);

        orderMaster.setTax(HomeActivity.vocher_Items.get(0).getTax());

        if (i == 1) orderMaster.setConfirmState(1);
        else orderMaster.setConfirmState(0);
        appDatabase.ordersMasterDao().insertOrder(orderMaster);


    }

    void SaveDetialsVocher(int x) {
        int vohno = appDatabase.ordersMasterDao().getLastVoherNo();

        for (int i = 0; i < HomeActivity.vocher_Items.size(); i++) {
            OrdersDetails ordersDetails = new OrdersDetails();
            ordersDetails.setVhfNo(vohno);
            ordersDetails.setDiscount(HomeActivity.vocher_Items.get(i).getDiscount());
            ordersDetails.setItemNo(HomeActivity.vocher_Items.get(i).getItemNCode());

            ordersDetails.setPrice(HomeActivity.vocher_Items.get(i).getPrice());
            ordersDetails.setDate(generalMethod.getCurentTimeDate(1));
            ordersDetails.setTime(generalMethod.getCurentTimeDate(2));

            ordersDetails.setTotalDiscVal(HomeActivity.vocher_Items.get(i).getTaxValue());



            ordersDetails.setIsPosted(0);
            if (x == 1) ordersDetails.setConfirmState(1);
            else ordersDetails.setConfirmState(0);
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
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }


   void CalculateTax(int taxType , List<Item> items){

     if(taxType==0)   // taxType=0 خاضع
     {   for (int i = 0; i < items.size(); i++) {

            itemTax = items.get(i).getAmount() * items.get(i).getTaxPercent() * 0.01;
            itemTotal = items.get(i).getAmount();

            itemTotalAfterTax =itemTotal + itemTax;
            subTotal = subTotal + itemTotal;
        }



//*********************************************************************************************
        for (int i = 0; i < items.size(); i++) {
            itemTotal = items.get(i).getAmount();
            itemTotalPerc = itemTotal / subTotal;
            itemDiscVal = (itemTotalPerc * totalDiscount);
            items.get(i).setTotalDiscVal(itemDiscVal);


            itemTotal = itemTotal - itemDiscVal;
            itemTax = itemTotal * items.get(i).getTaxPercent() * 0.01;




            items.get(i).setTaxValue(itemTax);
            totalTaxValue = totalTaxValue + itemTax;
        }
//****************************************************************************************
        netTotal = netTotal + subTotal - totalDiscount + totalTaxValue;


    }
    else if(taxType==1)//taxType=1;شامل
    {

/*
        for (int i = 0; i < items.size(); i++) {

            itemTax = items.get(i).getAmount() -
                    (items.get(i).getAmount() / (1 + items.get(i).getTaxPercent() * 0.01));

            itemTotal = items.get(i).getAmount() - itemTax;
            itemTotalAfterTax = items.get(i).getAmount();
            subTotal = subTotal + itemTotal;
        }

        for (int i = 0; i < items.size(); i++) {



            itemTax = items.get(i).getAmount() -
                    (items.get(i).getAmount() / (1 + items.get(i).getTaxPercent() * 0.01));


            itemTotal = items.get(i).getAmount() - itemTax;
            itemTotalPerc = itemTotal / subTotal;
            itemDiscVal = (itemTotalPerc * totalDiscount);
            items.get(i).setVoucherDiscount((double) itemDiscVal);
            items.get(i).setTotalDiscVal(itemDiscVal);
            itemTotal = itemTotal - itemDiscVal;


            itemTax = itemTotal * items.get(i).getTaxPercent() * 0.01;



            items.get(i).setTaxValue(itemTax);
            totalTaxValue = totalTaxValue + itemTax;
        }

//            totalDiscount+=getTotalDiscSetting(netTotal);
        Log.e("TOTAL", "noTax3totalTaxValue==" +totalTaxValue);
        netTotal = netTotal + subTotal - totalDiscount + totalTaxValue; // tahani -discount_oofers_total
        totalDiscount+=getTotalDiscSetting(netTotal);

        netTotal=netTotal-getTotalDiscSetting(netTotal);*/
    }
    }
}