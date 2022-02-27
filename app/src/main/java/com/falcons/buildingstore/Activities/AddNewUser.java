package com.falcons.buildingstore.Activities;

import static com.falcons.buildingstore.Activities.HomeActivity.vocher_Items;
import static com.falcons.buildingstore.Activities.LoginActivity.CONO_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.IP_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.PORT_PREF;
import static com.falcons.buildingstore.Activities.LoginActivity.SETTINGS_PREFERENCES;
import static com.falcons.buildingstore.Utilities.GeneralMethod.showSweetDialog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Utilities.ExportData;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.User;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.ImportData;
import com.google.android.material.badge.BadgeDrawable;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class AddNewUser extends AppCompatActivity {
    EditText username, passEdt;
    Spinner usertype;
    RadioGroup dis_Per;
    RadioButton RB_yes, RB_no;
    CircularProgressButton adduser;
    AppDatabase appDatabase;
    GeneralMethod generalMethod;
    BottomNavigationView bottomNavigationView;
    ExportData exportData;
    ImportData importData;
    private String ipAddress, port, coNo;
    private List<User> allUsers;
    private BadgeDrawable badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        init();

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        port = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_new_User();
            }
        });
    }

    void init() {
        generalMethod = new GeneralMethod(AddNewUser.this);

        username = findViewById(R.id.username);
        passEdt = findViewById(R.id.passEdt);
        usertype = findViewById(R.id.usertype);
        dis_Per = findViewById(R.id.dis_Per);
        RB_yes = findViewById(R.id.RB_yes);
        RB_no = findViewById(R.id.RB_no);
        adduser = findViewById(R.id.adduser);
        appDatabase = AppDatabase.getInstanceDatabase(AddNewUser.this);
        exportData = new ExportData(AddNewUser.this);
        importData = new ImportData(AddNewUser.this);
        allUsers = new ArrayList<>();

        bottomNavigationView = (BottomNavigationView)
                findViewById(R.id.bottom_navigation);

        badge = bottomNavigationView.getOrCreateBadge(R.id.action_cart);
        badge.setVisible(true);
        badge.setNumber(vocher_Items.size());

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

                        final Dialog dialog = new Dialog(AddNewUser.this);
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

                        dialog.findViewById(R.id.adduser).setVisibility(View.GONE);

                        dialog.findViewById(R.id.addCustomer).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                startActivity(new Intent(getApplicationContext(), AddNewCustomer.class));
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

    void Add_new_User() {

        if (!username.getText().toString().trim().equals("")
                && !passEdt.getText().toString().trim().equals("")) {

            int sameUsers = appDatabase.usersDao().getSameUsers(username.getText().toString().trim());
            if (sameUsers == 0) {

                User user = new User();
                user.setUserName(username.getText().toString());
                user.setUserPassword(passEdt.getText().toString());
                user.setUserType(usertype.getSelectedItem().toString().equals(getResources().getStringArray(R.array.user_type)[1]) ? 0 : 1);
                if (RB_yes.isChecked())
                    user.setDiscPermission(1);
                else
                    user.setDiscPermission(0);
                appDatabase.usersDao().insertUser(user);
                ArrayList<User> users = new ArrayList<>();
                users.add(user);
                exportData.addUser(users, new ExportData.AddUserCallBack() {
                    @Override
                    public void onResponse(String response) {
                        if (response.contains("Saved Successfully")) {


                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                @Override
                                public void onResponse(JSONArray response) {

                                    appDatabase.usersDao().deleteAll();
                                    allUsers.clear();
                                    showSweetDialog(AddNewUser.this, 1, getString(R.string.savedSuccsesfule), null);


                                    for (int i = 0; i < response.length(); i++) {

                                        try {

                                            allUsers.add(new User(
                                                    response.getJSONObject(i).getString("SALESNO"),
                                                    response.getJSONObject(i).getString("ACCNAME"),
                                                    response.getJSONObject(i).getString("USER_PASSWORD"),
                                                    Integer.parseInt(response.getJSONObject(i).getString("USERTYPE")),
                                                    Integer.parseInt(response.getJSONObject(i).getString("DISCOUNTPER")),
                                                    1));

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                    appDatabase.usersDao().addAll(allUsers);

                                }

                                @Override
                                public void onError(String error) {

                                }
                            }, ipAddress, port, coNo);

                            username.setText("");
                            passEdt.setText("");

                        }
                    }

                    @Override
                    public void onError(String error) {

                    }
                });


            } else {

                username.setError(getString(R.string.user_already_exists));

            }

        } else if (username.getText().toString().trim().equals(""))
            username.setError(getResources().getString(R.string.Empty));
        else if (passEdt.getText().toString().trim().equals(""))
            username.setError(getResources().getString(R.string.Empty));


    }
    @Override
    public void onBackPressed() {

    }
}