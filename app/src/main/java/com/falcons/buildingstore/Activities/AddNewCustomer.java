package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddNewCustomer extends AppCompatActivity {
    EditText cus_name, phoneNo;
    CircularProgressButton addCustomer;
    AppDatabase appDatabase;
    GeneralMethod generalMethod;
    BottomNavigationView bottomNavigationView;
    ExportData exportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_customer);
        init();

        addCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_new_Customer();
            }
        });
    }

    void init() {
        appDatabase = AppDatabase.getInstanceDatabase(AddNewCustomer.this);
        generalMethod = new GeneralMethod(AddNewCustomer.this);
        cus_name = findViewById(R.id.cus_name);
        phoneNo = findViewById(R.id.phoneNo);
        addCustomer = findViewById(R.id.addCustomer);
        exportData = new ExportData(AddNewCustomer.this);

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.action_add);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.action_home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.action_cart:

                        startActivity(new Intent(getApplicationContext(), BasketActivity.class));
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

                        return true;
                }
                return false;
            }
        });

    }

    void Add_new_Customer() {
        if (!cus_name.getText().toString().trim().equals("")
                && phoneNo.getText().toString().trim().length() > 4) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setCustomerName(cus_name.getText().toString().trim());
            customerInfo.setPhoneNo(phoneNo.getText().toString().trim());
            appDatabase.customersDao().insertCustm(customerInfo);
            generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
            cus_name.setText("");
            phoneNo.setText("");
        } else if (cus_name.getText().toString().trim().equals("")) {
            cus_name.setError(getResources().getString(R.string.Empty));

        } else if (phoneNo.getText().toString().trim().length() <= 4) {
            phoneNo.setError(getResources().getString(R.string.Empty));
        }


    }
}