package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.falcons.buildingstore.Adapters.CustomersAdapter;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    public static Spinner customersSpinner;
    FloatingActionButton addCustomerBtn;
    List<CustomerInfo> customersList_sp;
    List<Item> itemList_rv;
    public static List<String> customerNames_sp;
    public static Dialog custmsDialog;
    RecyclerView itemsRV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        customerNames_sp.add(getString(R.string.customerName));
        for (int i = 0; i < 5; i++) {

            customerNames_sp.add("Customer" + i);
            customersList_sp.add(new CustomerInfo("Customer" + i, "078" + i + (i + 1) + (i + 1)));

        }
        customersList_sp.add(new CustomerInfo("Customer" + 5, "078" + 5 + (5 + 1) + (5 + 1)));


        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, customerNames_sp);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        customersSpinner.setAdapter(arrayAdapter);
        customersSpinner.setSelection(0);
        addCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                custmsDialog = new Dialog(HomeActivity.this);
                custmsDialog.setCancelable(false);
                custmsDialog.setContentView(R.layout.customers_dialog);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(custmsDialog.getWindow().getAttributes());
                lp.width = (int)(getResources().getDisplayMetrics().widthPixels/1.15);
                lp.height = (int)(getResources().getDisplayMetrics().heightPixels/1.7);
                custmsDialog.getWindow().setAttributes(lp);
                custmsDialog.show();

                ImageButton closeBtn = custmsDialog.findViewById(R.id.closeBtn);

                RecyclerView customersRV = custmsDialog.findViewById(R.id.customersRV);

                CustomersAdapter customersAdapter = new CustomersAdapter(HomeActivity.this, customersList_sp);

                customersRV.setAdapter(customersAdapter);

                closeBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        custmsDialog.dismiss();
                    }
                });

            }
        });


        /* Initializing RecyclerView */

        for (int i = 0; i < 6; i++) {

            itemList_rv.add(new Item("ItemName" + i, "10000" + i, "20000" + i,
                    "3" + i, "13" + i, "JD", "*******", "FOOD", i + "%"));

        }

        ItemsAdapter itemsAdapter = new ItemsAdapter(itemList_rv, this);
        itemsRV.setAdapter(itemsAdapter);


    }

    void init() {

        customersSpinner = findViewById(R.id.customersSpinner);
        addCustomerBtn = findViewById(R.id.addCustomerBtn);
        itemsRV = findViewById(R.id.itemsRV);
        customersList_sp = new ArrayList<>();
        customerNames_sp = new ArrayList<>();
        itemList_rv = new ArrayList<>();


        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        String ipAddress = sharedPref.getString(IP_PREF, "");

        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", sharedPref.getInt(PORT_PREF, 0) + "");
        Log.e("CONO_PREF2", sharedPref.getInt(CONO_PREF, 0) + "");

    }

}