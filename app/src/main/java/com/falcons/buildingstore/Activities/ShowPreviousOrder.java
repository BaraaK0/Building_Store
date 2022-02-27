package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.HomeActivity.vocher_Items;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.falcons.buildingstore.Adapters.OrderShowAdapter;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.OrderMaster;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.ExportData;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.invoke.LambdaConversionException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ShowPreviousOrder extends AppCompatActivity {
    RecyclerView orderRec;
    OrderShowAdapter orderShowAdapter;
    RecyclerView.LayoutManager layoutManager;
  public static   List<OrderMaster> orderMasters;
    List<String> Cus_name = new ArrayList<>();
    Calendar myCalendar;
    TextView date, OrderNO;
    AppDatabase appDatabase;
    Spinner Customer_spinner;
    Button preview;
    BottomNavigationView bottomNavigationView;
    ExportData exportData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_previous_order);
        init();
        Cus_name = appDatabase.customersDao().getCustmName();
        Cus_name.add(0, "No Filter");
        Log.e("Cus_name==", Cus_name.size() + "");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                Cus_name);
        Customer_spinner.setAdapter(spinnerArrayAdapter);
        layoutManager = new LinearLayoutManager(ShowPreviousOrder.this);
        orderRec.setLayoutManager(layoutManager);
        orderMasters = appDatabase.ordersMasterDao().getAllOrders();// from voucher master

        orderShowAdapter = new OrderShowAdapter(ShowPreviousOrder.this, orderMasters);
        orderRec.setAdapter(orderShowAdapter);
        myCalendar = Calendar.getInstance();

        Date currentTimeAndDate = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        String today = df.format(currentTimeAndDate);
        date.setText(today);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(ShowPreviousOrder.this, openDatePickerDialog(0), myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderMasters.clear();

                if (!OrderNO.getText().toString().equals("")
                        && !Customer_spinner.getSelectedItem().equals("No Filter")) {
                    int cus_id = appDatabase.customersDao().getCustmByName(Customer_spinner.getSelectedItem().toString());
                    orderMasters = appDatabase.ordersMasterDao().getOrdersByOrderNOandCusID(OrderNO.getText().toString().trim(), cus_id, date.getText().toString());// from voucher master

                } else if (!OrderNO.getText().toString().equals("")
                        && Customer_spinner.getSelectedItem().equals("No Filter")) {
                    orderMasters = appDatabase.ordersMasterDao().getOrdersByOrderNO(OrderNO.getText().toString(), date.getText().toString());// from voucher master

                } else if (OrderNO.getText().toString().equals("")
                        && !Customer_spinner.getSelectedItem().equals("No Filter")) {
                    int cus_id = appDatabase.customersDao().getCustmByName(Customer_spinner.getSelectedItem().toString());
                    orderMasters = appDatabase.ordersMasterDao().getOrdersByCusID(cus_id, date.getText().toString());// from voucher master

                } else if (OrderNO.getText().toString().equals("")
                        && Customer_spinner.getSelectedItem().equals("No Filter")) {
                    orderMasters = appDatabase.ordersMasterDao().getOrdersByDate(date.getText().toString());// from voucher master

                }


                orderShowAdapter = new OrderShowAdapter(ShowPreviousOrder.this, orderMasters);
                orderRec.setAdapter(orderShowAdapter);
            }
        });
    }

    void init() {
        preview = findViewById(R.id.preview);
        OrderNO = findViewById(R.id.ORDERNO);
        date = findViewById(R.id.SH_date);
        Customer_spinner = findViewById(R.id.Cus_name);
        appDatabase = AppDatabase.getInstanceDatabase(ShowPreviousOrder.this);
        orderRec = findViewById(R.id.ordersRec);
        exportData = new ExportData(ShowPreviousOrder.this);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        BadgeDrawable badge = bottomNavigationView.getOrCreateBadge(R.id.action_cart);
        badge.setVisible(true);
        badge.setNumber(vocher_Items.size());

        bottomNavigationView.setSelectedItemId(R.id.action_report);

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
                        return true;

                    case R.id.action_add:
                        final Dialog dialog = new Dialog(ShowPreviousOrder.this);
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

    public DatePickerDialog.OnDateSetListener openDatePickerDialog(final int flag) {
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(flag);
            }

        };
        return date;
    }

    private void updateLabel(int flag) {

        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        if (flag == 0)
            date.setText(sdf.format(myCalendar.getTime()));

    }

    public static void startActivity(Context context) {

    }
    @Override
    public void onBackPressed() {

    }
}