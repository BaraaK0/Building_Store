package com.falcons.buildingstore.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.EditText;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

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

                        startActivity(new Intent(getApplicationContext(), ShowPreviousOrder.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.action_add:

                        UserLogs userLogs = appDatabase.userLogsDao().getLastuserLogin();

                        int userType = appDatabase.usersDao().getUserType(userLogs.getUserID());

                        if (userType == 1) {


                            final Dialog dialog = new Dialog(AddNewCustomer.this);
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

                            dialog.findViewById(R.id.addCustomer).setVisibility(View.GONE);

                            dialog.findViewById(R.id.adduser).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    startActivity(new Intent(getApplicationContext(), AddNewUser.class));
                                    overridePendingTransition(0, 0);
                                    dialog.dismiss();
                                }
                            });
                        }

                        return true;
                }
                return false;
            }
        });

    }

    void Add_new_Customer() {
        if (!cus_name.getText().toString().trim().equals("")
                && phoneNo.getText().toString().trim().length() != 0) {
            CustomerInfo customerInfo = new CustomerInfo();
            customerInfo.setCustomerName(cus_name.getText().toString().trim());
            customerInfo.setPhoneNo(phoneNo.getText().toString().trim());
            appDatabase.customersDao().insertCustm(customerInfo);
            ArrayList<CustomerInfo> customers = new ArrayList<>();
            customers.add(customerInfo);
            exportData.addCustomer(customers);
            cus_name.setText("");
            phoneNo.setText("");
        } else if (cus_name.getText().toString().trim().equals("")) {
            cus_name.setError(getResources().getString(R.string.Empty));

        } else if (phoneNo.getText().toString().trim().length() == 0) {
            phoneNo.setError(getResources().getString(R.string.Empty));
        }


    }
}