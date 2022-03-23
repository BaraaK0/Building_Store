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
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;

import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;


import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.Adapters.ItemsAdapter;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.ImportData;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity {

    public static List<Item> allItemList_rv= new ArrayList<>();;
    private List<Item> search_itemList;


    private String ipAddress, ipPort, coNo;

    public ItemsAdapter itemsAdapter;

    public static RecyclerView itemsRecycler;

    BottomNavigationView bottom_navigation;

    ImportData importData;

    private AppDatabase appDatabase;

    public static EditText searchItemsEdt;
    private TextInputLayout searchItems_textField;
    private ImageView ic_clear;

    public static final int REQUEST_Camera_Barcode = 1;

    ExportData exportData;
    public static TextView itemcount;
    public static int item_count = 0;
    public static ArrayList<Item> itemList;
    public static ArrayList<Item> vocher_Items = new ArrayList<>();
    public static BadgeDrawable badge;
    ImageButton backBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();

        itemsRecycler.setHasFixedSize(true);
        itemsRecycler.setItemViewCacheSize(20);
        itemsRecycler.setDrawingCacheEnabled(true);
        itemsRecycler.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);


        /*  Initialize Items  */

        if(HomeActivity.vocher_Items.size() ==0)

        {

            allItemList_rv = appDatabase.itemsDao().getAllItems();

            itemsAdapter = new ItemsAdapter(allItemList_rv, HomeActivity.this);
            itemsRecycler.setAdapter(itemsAdapter);
            itemsRecycler.setSaveEnabled(true);
            Log.e("allItemList_rv",allItemList_rv.size()+"");

        }
        else
        {


            itemsAdapter = new ItemsAdapter(allItemList_rv, HomeActivity.this);
            itemsRecycler.setAdapter(itemsAdapter);
            itemsRecycler.setSaveEnabled(true);
        }

        /* Items Search Handling */

        ic_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItemsEdt.setText("");
            }
        });

        searchItemsEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchItemsEdt.setSelection(0, searchItemsEdt.getText().toString().length());
            }
        });

        searchItemsEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String eTxt = s.toString().trim().toLowerCase();

                if (!eTxt.equals("")) {

                    search_itemList.clear();
                    for (int i = 0; i < allItemList_rv.size(); i++) {

                        if (allItemList_rv.get(i).getItemName().toLowerCase().contains(eTxt) ||
                                allItemList_rv.get(i).getItemNCode().toLowerCase().contains(eTxt.replaceAll("\\s+", "")) ||
                                allItemList_rv.get(i).getItemOCode().toLowerCase().contains(eTxt.replaceAll("\\s+", ""))) {

                            search_itemList.add(allItemList_rv.get(i));

                        }

                    }

                    ItemsAdapter itemsAdapter1 = new ItemsAdapter(search_itemList, HomeActivity.this);
                    itemsRecycler.setAdapter(itemsAdapter1);

                }
                else {

                    itemsAdapter = new ItemsAdapter(allItemList_rv, HomeActivity.this);
                    itemsRecycler.setAdapter(itemsAdapter);

                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
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

        /* Bottom Navigation */


        bottom_navigation.setSelectedItemId(R.id.action_home);

        bottom_navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {

                            case R.id.action_home:

                                return true;

                            case R.id.action_cart:

                                startActivity(new Intent(getApplicationContext(), BasketActivity.class));
                                overridePendingTransition(0, 0);
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

                                AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
                                builder.setMessage(getString(R.string.export_data_msg))
                                        .setTitle(getString(R.string.export_data))
                                        .setPositiveButton(getString(R.string.yes), dialogClickListener)
                                        .setNegativeButton(getString(R.string.cancel), dialogClickListener)
                                        .show();                                return true;

                            case R.id.action_report:

                                startActivity(new Intent(getApplicationContext(), ShowPreviousOrder.class));
                                overridePendingTransition(0, 0);
                                return true;

                            case R.id.action_add:

                                final Dialog dialog = new Dialog(HomeActivity.this);
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
                                Log.e("UserType/Username", userType+"/"+userLogs.getUserName());
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


    void init() {
        exportData = new ExportData(HomeActivity.this);
        backBtn = findViewById(R.id.backBtn);
        importData = new ImportData(this);
        appDatabase = AppDatabase.getInstanceDatabase(this);

        bottom_navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        itemsRecycler = findViewById(R.id.itemsRecycler);

        itemcount = findViewById(R.id.itemcount);
        searchItemsEdt = findViewById(R.id.searchItemsEdt);
        searchItems_textField = findViewById(R.id.searchItems_textField);
        ic_clear = findViewById(R.id.ic_clear);


        search_itemList = new ArrayList<>();

        badge = bottom_navigation.getOrCreateBadge(R.id.action_cart);
        badge.setVisible(true);
        badge.setNumber(vocher_Items.size());

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");
        Log.e("IP_PREF2", ipAddress + "");
        Log.e("PORT_PREF2", ipPort);
        Log.e("CONO_PREF2", coNo);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_Camera_Barcode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(HomeActivity.this, ScanActivity.class);
                i.putExtra("key", "1");
                startActivity(i);
            } else {
                Toast.makeText(this, "Camera permission denied !", Toast.LENGTH_LONG).show();
            }
        }
    }
    @Override
    public void onBackPressed() {

    }
}