package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.HomeActivity.vocher_Items;
import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.falcons.buildingstore.Adapters.BasketItemAdapter;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.OrdersDetails;

import com.falcons.buildingstore.R;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;
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
    private MaterialCardView basketListCard;
    public static BadgeDrawable badge;
    private ArrayList<OrdersDetails> ordersDetailslist =new ArrayList<>();
    String Cus_selection;
    TextView Textveiw_total;
    int VOHNO=0;

    int vohno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_basket);
        init();
        fillListAdapter();


         customerTv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long rowId) {
                Cus_selection = (String) parent.getItemAtPosition(position);
       customer_textInput.setError(null);
            }
        });



        /*  Initialize Customers  */
        allCustomers = appDatabase.customersDao().getAllCustms();
        customerNames = new ArrayList<>();

        for (int i = 0; i < allCustomers.size(); i++) {

            customerNames.add(allCustomers.get(i).getCustomerName());

        }

        ArrayAdapter<String> customersAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, customerNames);

        customerTv.setAdapter(customersAdapter);



        try {
            if(OrderReport.Report_VOHNO!=0){
                Log.e("OrderReport.Cusname=",OrderReport.Cusname);
                for(int i=0;i<customerNames.size();i++)
                    if(customerNames.get(i).trim().equals(OrderReport.Cusname.trim()))
                    {   Log.e("i=",i+"");
                        customerTv.setText(customerTv.getAdapter().getItem(i).toString(), false);
                        Cus_selection =customerTv.getAdapter().getItem(i).toString();
                        }

            }
        }catch (Exception exception){
            Log.e("exception",exception.getMessage());
        }



        orderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedCustomer = customerTv.getText().toString().trim();

                    if (HomeActivity.vocher_Items.size() != 0) {

                        if (customerNames.contains(selectedCustomer) && !selectedCustomer.equals("")) {
                            customer_textInput.setError(null);
                            try {
                                SaveDetialsVocher(1);
                                SaveMasterVocher(1);
                                ordersDetailslist.clear();
                                HomeActivity.vocher_Items.clear();
                                fillListAdapter();
                                Textveiw_total.setText("0.0");
                            } catch (Exception exception) {
                                Log.e("exception==", exception.getMessage());

                            }

                            HomeActivity.item_count = 0;
                            generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                        } else {

                            customer_textInput.setError("*");

                        }


                    } else {
                        generalMethod.showSweetDialog(BasketActivity.this, 3, getResources().getString(R.string.fillbasket), "");

                    }


            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String selectedCustomer = customerTv.getText().toString().trim();

                if (HomeActivity.vocher_Items.size() != 0) {

                    if (customerNames.contains(selectedCustomer) && !selectedCustomer.equals("")) {
                        customer_textInput.setError(null);
//                        try {
                            SaveDetialsVocher(2);
                            SaveMasterVocher(2);
                            ordersDetailslist.clear();
                            HomeActivity.vocher_Items.clear();
                            fillListAdapter();
                        Textveiw_total.setText("0.0");
//                        } catch (Exception exception) {
//                            Log.e("exception==", exception.getMessage());
//
//                        }

                        HomeActivity.item_count = 0;
                        generalMethod.showSweetDialog(BasketActivity.this, 1, getResources().getString(R.string.savedSuccsesfule), "");
                    } else {

                        customer_textInput.setError("*");

                    }


                } else {
                    generalMethod.showSweetDialog(BasketActivity.this, 3, getResources().getString(R.string.fillbasket), "");

                }

            }

        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



      CalculateTotal();
    }


    void init() {
        //Get the bundle
        Bundle bundle = getIntent().getExtras();

        //Extract the data???
 /*   VOHNO = bundle.getInt("Report_VOHNO");
        Log.e("VOHNO",VOHNO+"");*/
//        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
//
//        vohno =Integer.parseInt(sharedPref.getString(LoginActivity.maxVoch_PREF, "")) ;
        Textveiw_total= findViewById(R.id.total);
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
        basketListCard = findViewById(R.id.basketListCard);
        allCustomers = new ArrayList<>();

        basketListCard.requestLayout();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.height = (int) (getResources().getDisplayMetrics().heightPixels / 2.5);
        basketListCard.getLayoutParams().height = lp.height;

        badge = bottom_navigation.getOrCreateBadge(R.id.action_cart);
        badge.setVisible(true);
        badge.setNumber(vocher_Items.size());


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

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Yes button clicked
                                        exportData.exportSalesVoucherM();                                        dialog.dismiss();
                                        break;


                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //No button clicked
                                        dialog.dismiss();
                                        break;

                                }
                            }
                        };

                        AlertDialog.Builder builder = new AlertDialog.Builder(BasketActivity.this);
                        builder.setMessage(getString(R.string.export_data_msg))
                                .setTitle(getString(R.string.export_data))
                                .setPositiveButton(getString(R.string.yes), dialogClickListener)
                                .setNegativeButton(getString(R.string.cancel), dialogClickListener)
                                .show();                        return true;

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
        BasketItemAdapter basketItemAdapter = new BasketItemAdapter(vocher_Items, BasketActivity.this);
        BasketItem.setAdapter(basketItemAdapter);

    }

    void SaveMasterVocher(int i) {

        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setIsPosted(0);
        orderMaster.setDate(generalMethod.getCurentTimeDate(1));
        orderMaster.setTime(generalMethod.getCurentTimeDate(2));
       // orderMaster.setCustomerId();
        orderMaster.setDiscount(vocher_Items.get(0).getDiscount());
       orderMaster.setCustomerId(appDatabase.customersDao().getCustmByName(Cus_selection) );
        orderMaster.setDiscount(HomeActivity.vocher_Items.get(0).getDiscount());
        UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();
        orderMaster.setUserNo(userLogs.getUserID());

        Log.e("netTotal444==",netTotal+"");

        orderMaster.setNetTotal(netTotal);
        Log.e("netTotal777==",orderMaster.getNetTotal()+"");



        double totalnetsales=0;
        double totalqty=0;
        double totaltax=0;
        double subtotal=0;
        for (int x = 0; x < ordersDetailslist.size(); x++) {
            totalqty+=ordersDetailslist.get(x).getQty();
            totalnetsales+= ordersDetailslist.get(x).getNetTotal();
            totaltax+= ordersDetailslist.get(x).getTaxValue();
            subtotal+= ordersDetailslist.get(x).getSubtotal();
        }
        orderMaster.setTotalQty(totalqty);
        orderMaster.setNetTotal(totalnetsales);
        orderMaster.setTax(totaltax);
        orderMaster.setSubTotal(subtotal);
        if (i == 1)
            orderMaster.setConfirmState(1);
        else
            orderMaster.setConfirmState(0);


        if(OrderReport.Report_VOHNO!=0){
            Log.e("Report_VOHNO==",OrderReport.Report_VOHNO+"");
            orderMaster.setVhfNo(OrderReport.Report_VOHNO);
            appDatabase.ordersMasterDao().deleteOrderByVOHNO(OrderReport.Report_VOHNO);
            appDatabase.ordersDetails_dao().deleteOrderByVOHNO(OrderReport.Report_VOHNO);
            appDatabase.ordersMasterDao().insertOrder(orderMaster);

            for (int l = 0; l < ordersDetailslist.size(); l++) {
                ordersDetailslist.get(l).setVhfNo(OrderReport.Report_VOHNO);
                appDatabase.ordersDetails_dao().insertOrder(ordersDetailslist.get(l));
            }
        }
        else{
            SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
vohno =Integer.parseInt(sharedPref.getString(LoginActivity.maxVoch_PREF, "")) ;
          // vohno =Integer.parseInt(vohno) ;
            orderMaster.setVhfNo(vohno);
            appDatabase.ordersMasterDao().insertOrder(orderMaster);

            for (int l = 0; l < ordersDetailslist.size(); l++) {
                ordersDetailslist.get(l).setVhfNo(vohno);
                appDatabase.ordersDetails_dao().insertOrder(ordersDetailslist.get(l));
            }
        }

        ordersDetailslist.clear();
         HomeActivity.vocher_Items.clear();
         UpdateMaxVo();
        badge.setNumber(0);
             fillListAdapter();
        OrderReport.Report_VOHNO=0;
    }
    void   UpdateMaxVo(){

        SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
        Log.e("vohno==",vohno+"");
        editor.putString(LoginActivity.maxVoch_PREF,String.valueOf(++vohno) );
        editor.apply();
    }
    void SaveDetialsVocher(int x) {
        Log.e("vocher_ItemsSize==",HomeActivity.vocher_Items.size()+"");
        for (int i = 0; i < HomeActivity.vocher_Items.size(); i++) {
            OrdersDetails ordersDetails = new OrdersDetails();

            ordersDetails.setDiscount(HomeActivity.vocher_Items.get(i).getDiscount());
            ordersDetails.setItemNo(HomeActivity.vocher_Items.get(i).getItemOCode());
            ordersDetails.setItemName(HomeActivity.vocher_Items.get(i).getItemName());
            ordersDetails.setQty(HomeActivity.vocher_Items.get(i).getQty());
            ordersDetails.setPrice(HomeActivity.vocher_Items.get(i).getPrice());
            ordersDetails.setDate(generalMethod.getCurentTimeDate(1));
            ordersDetails.setTime(generalMethod.getCurentTimeDate(2));
            ordersDetails.setAmount(HomeActivity.vocher_Items.get(i).getAmount());
            ordersDetails.setTaxPercent(HomeActivity.vocher_Items.get(i).getTaxPercent());
            ordersDetails.setUnit(HomeActivity.vocher_Items.get(i).getUnit());
            if(ordersDetails.getUnit().equals("One Unit"))
                ordersDetails.setWhichUNIT("0");
            else
                ordersDetails.setWhichUNIT("1");

            ordersDetails.setWhichUNITSTR(HomeActivity.vocher_Items.get(i).getUnit());
          double  num_items = appDatabase.itemUnitsDao().getConvRatebyitemnum( ordersDetails.getItemNo());
            ordersDetails.setWHICHUQTY(num_items+"");
            ordersDetails.setCustomerId(appDatabase.customersDao().getCustmByName(Cus_selection));
            ordersDetails.setIsPosted(0);
            ordersDetails.setArea(HomeActivity.vocher_Items.get(i).getArea());

//            ordersDetails.setUnit(vocher_Items.get(i).getUnit().equals("One Unit") ? 0 : 1);

            //Discount calcualtios
            ordersDetails.setTotalDiscVal( HomeActivity.vocher_Items.get(i).getAmount()* HomeActivity.vocher_Items.get(i).getDiscount());
            ordersDetails.setTotal(HomeActivity.vocher_Items.get(i).getAmount()- ordersDetails.getTotalDiscVal());

               //Tax calcualtios

            ordersDetails.setTax(HomeActivity.vocher_Items.get(i).getPrice()*HomeActivity.vocher_Items.get(i).getTaxPercent());

            ordersDetails.setTaxValue(ordersDetails.getTax()*HomeActivity.vocher_Items.get(i).getQty());

     // ?????????? ??????????
            double subtotal=0;
            subtotal= ordersDetails.getAmount()-ordersDetails.getTaxValue()-ordersDetails.getTotalDiscVal();

            ordersDetails.setSubtotal(subtotal);
             Log.e("ordersDetails.getSubtotal()=",ordersDetails.getSubtotal()+"");

            double nettotal=0;
            nettotal=ordersDetails.getSubtotal()+ordersDetails.getTaxValue();
                    ordersDetails.setNetTotal(nettotal);
            Log.e("ordersDetails.getNetTotal()=",ordersDetails.getNetTotal()+"");



            // ?????????? ??????????
      /*    double subtotal=ordersDetails.getAmount()-ordersDetails.getDiscount();
            ordersDetails.setSubtotal(subtotal);*/





            if (x == 1) ordersDetails.setConfirmState(1);
            else ordersDetails.setConfirmState(0);


            ordersDetailslist.add(ordersDetails);
            Log.e("hereordersDetailslist==",ordersDetailslist.size()+"");




            Log.e("ordersDetails.getarea",ordersDetails.getArea()+"");
            Log.e("ordersDetails.getAmount()=",ordersDetails.getAmount()+"");
            Log.e("ordersDetails.getTaxValue()=",ordersDetails.getTaxValue()+"");
            Log.e("ordersDetails.getDiscount()=",ordersDetails.getDiscount()+"");






        }


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

    }

    void CalculateTotal(){

try {


    double setTotalDiscVal = 0;
    double total = 0;
    for (int i = 0; i < HomeActivity.vocher_Items.size(); i++) {
        setTotalDiscVal = HomeActivity.vocher_Items.get(i).getAmount() * HomeActivity.vocher_Items.get(i).getDiscount();
        total += HomeActivity.vocher_Items.get(i).getAmount() - setTotalDiscVal;


    }

    Textveiw_total.setText(total + "");

}catch (Exception exception){
    Log.e("exception=",exception.getMessage());
}
    }
//   void CalculateTax(int taxType , List<Item> items){
//
//     if(taxType==0)   // taxType=0 ????????
//     {   for (int i = 0; i < items.size(); i++) {
//
//         items.get(i).setTaxValue(items.get(i).getTaxPercent()*items.get(i).getQty());
//         items.get(i).setTax(items.get(i).getPrice()*items.get(i).getTaxPercent());
//         items.get(i).setto(items.get(i).getPrice()*items.get(i).getTaxPercent());
//        }
//
//
//
////*********************************************************************************************
//        for (int i = 0; i < items.size(); i++) {
//
//
//
//
//
//        }
////****************************************************************************************
//
//
//    }
//    else if(taxType==1)//taxType=1;????????
//    {
//
///*
//        for (int i = 0; i < items.size(); i++) {
//
//            itemTax = items.get(i).getAmount() -
//                    (items.get(i).getAmount() / (1 + items.get(i).getTaxPercent() * 0.01));
//
//            itemTotal = items.get(i).getAmount() - itemTax;
//            itemTotalAfterTax = items.get(i).getAmount();
//            subTotal = subTotal + itemTotal;
//        }
//
//        for (int i = 0; i < items.size(); i++) {
//
//
//
//            itemTax = items.get(i).getAmount() -
//                    (items.get(i).getAmount() / (1 + items.get(i).getTaxPercent() * 0.01));
//
//
//            itemTotal = items.get(i).getAmount() - itemTax;
//            itemTotalPerc = itemTotal / subTotal;
//            itemDiscVal = (itemTotalPerc * totalDiscount);
//            items.get(i).setVoucherDiscount((double) itemDiscVal);
//            items.get(i).setTotalDiscVal(itemDiscVal);
//            itemTotal = itemTotal - itemDiscVal;
//
//
//            itemTax = itemTotal * items.get(i).getTaxPercent() * 0.01;
//
//
//
//            items.get(i).setTaxValue(itemTax);
//            totalTaxValue = totalTaxValue + itemTax;
//        }
//
////            totalDiscount+=getTotalDiscSetting(netTotal);
//        Log.e("TOTAL", "noTax3totalTaxValue==" +totalTaxValue);
//        netTotal = netTotal + subTotal - totalDiscount + totalTaxValue; // tahani -discount_oofers_total
//        totalDiscount+=getTotalDiscSetting(netTotal);
//
//        netTotal=netTotal-getTotalDiscSetting(netTotal);*/
//    }
//    }
}