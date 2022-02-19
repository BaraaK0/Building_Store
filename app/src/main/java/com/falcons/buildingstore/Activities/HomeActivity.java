package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.ImportData;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    public static List<Item> allItemList_rv;
    private List<Item> search_itemList;

    private List<CustomerInfo> allCustomers;
    public static ArrayList<String> customerNames;

    private String ipAddress, ipPort, coNo;

    public ItemsAdapter itemsAdapter;

    public static RecyclerView itemsRecycler;

    BottomNavigationView bottom_navigation;

    ImportData importData;

    private AppDatabase appDatabase;

    public static TextInputLayout customer_textInput;
    public static AutoCompleteTextView customerTv;
    public static EditText searchItemsEdt;
    private TextInputLayout searchItems_textField;
    private ImageView scanCodeBtn;

    public static final int REQUEST_Camera_Barcode = 1;

    ExportData exportData;
    public static TextView itemcount;
    public static int item_count = 0;
    public static ArrayList<Item> itemList;
    public static ArrayList<Item> vocher_Items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        bottom_navigation.setSelectedItemId(R.id.action_home);

        bottom_navigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_home:
                        return true;

                    case R.id.action_cart:
                        Intent intent = new Intent(HomeActivity.this, BasketActivity.class);
                        intent.putExtra("previous", "Home");
                        startActivity(intent);
                        return true;

                    case R.id.action_add:
                        return true;

                    case R.id.action_report:
                        return true;


                }
                return false;
            }
        });


        /*  Initialize Items  */
        allItemList_rv = appDatabase.itemsDao().getAllItems();

        itemsAdapter = new ItemsAdapter(allItemList_rv, HomeActivity.this);
        itemsRecycler.setAdapter(itemsAdapter);
        itemsRecycler.setSaveEnabled(true);

        /*  Initialize Customers  */
        allCustomers = appDatabase.customersDao().getAllCustms();
        customerNames = new ArrayList<>();

        for (int i = 0; i < allCustomers.size(); i++) {

            customerNames.add(allCustomers.get(i).getCustomerName());

        }

        ArrayAdapter<String> customersAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_dropdown_item_1line, customerNames);

        customerTv.setAdapter(customersAdapter);


        /* Items Search Handling */
//        searchItemsEdt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                String eTxt = s.toString().trim().toLowerCase();
//
//                if (!eTxt.equals("")) {
//
//                    search_itemList.clear();
//                    for (int i = 0; i < allItemList_rv.size(); i++) {
//
//                        if (allItemList_rv.get(i).getItemName().toLowerCase().contains(eTxt) ||
//                                allItemList_rv.get(i).getItemNCode().toLowerCase().contains(eTxt.replaceAll("\\s+", ""))) {
//
//                            search_itemList.add(allItemList_rv.get(i));
//
//                        }
//
//                    }
//
//                    ItemsAdapter itemsAdapter1 = new ItemsAdapter(search_itemList, HomeActivity.this);
//                    itemsRecycler.setAdapter(itemsAdapter1);
//
//                }
//                else {
//
//                    itemsAdapter = new ItemsAdapter(allItemList_rv, HomeActivity.this);
//                    itemsRecycler.setAdapter(itemsAdapter);
//
//                }
//
//            }
//        });

        searchItems_textField.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e("Helloooo", "HHHHHHEEEEEEEEHHHEE");

                if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(HomeActivity.this, new String[]{Manifest.permission.CAMERA}, REQUEST_Camera_Barcode);
                    if (ContextCompat.checkSelfPermission(HomeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {//just for first time
                        Log.e("requestResult", "PERMISSION_GRANTED");
                        Intent i = new Intent(HomeActivity.this, ScanActivity.class);
                        i.putExtra("key", "1");
                        startActivity(i);

                    }
                } else {
                    Intent i = new Intent(HomeActivity.this, ScanActivity.class);
                    i.putExtra("key", "1");
                    startActivity(i);
                }
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_home:
                                return true;

                            case R.id.action_cart:

                                Intent intent = new Intent(getApplicationContext(), BasketActivity.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);

                                return true;
                            case R.id.exportdata:

                                exportData.exportSalesVoucherM();
                                return true;

                            case R.id.action_report:

                                Intent intent1 = new Intent(getApplicationContext(), ShowPreviousOrder.class);
                                startActivity(intent1);
                                overridePendingTransition(0, 0);
                                return true;

                            case R.id.action_add:

                                final Dialog dialog = new Dialog(HomeActivity.this);
                                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                dialog.setCancelable(true);
                                dialog.setContentView(R.layout.adddailog);
                                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                                lp.copyFrom(dialog.getWindow().getAttributes());
                                lp.gravity = Gravity.CENTER;
                                dialog.getWindow().setAttributes(lp);
                                dialog.show();


                                dialog.findViewById(R.id.addCustomer).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent2 = new Intent(getApplicationContext(), AddNewCustomer.class);
                                        startActivity(intent2);
                                        overridePendingTransition(0, 0);
                                        dialog.dismiss();
                                    }
                                });
                                dialog.findViewById(R.id.adduser).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent intent2 = new Intent(getApplicationContext(), AddNewUser.class);
                                        startActivity(intent2);
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


    void init() {
        exportData = new ExportData(HomeActivity.this);

        importData = new ImportData(this);
        appDatabase = AppDatabase.getInstanceDatabase(this);

        bottom_navigation = findViewById(R.id.bottom_navigation);
        itemsRecycler = findViewById(R.id.itemsRecycler);

        itemcount = findViewById(R.id.itemcount);
        customer_textInput = findViewById(R.id.customer_textInput);
        customerTv = findViewById(R.id.customerTv);
        searchItemsEdt = findViewById(R.id.searchItemsEdt);
        searchItems_textField = findViewById(R.id.searchItems_textField);

        allItemList_rv = new ArrayList<>();
        search_itemList = new ArrayList<>();
        allCustomers = new ArrayList<>();

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", ipPort);
        Log.e("CONO_PREF2", coNo);

    }


}