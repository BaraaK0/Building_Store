package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.hardware.usb.UsbRequest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.User;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.CustomerInfo;
import com.falcons.buildingstore.Database.Entities.Item;
import com.falcons.buildingstore.R;
import com.falcons.buildingstore.Utilities.GeneralMethod;
import com.falcons.buildingstore.Utilities.ImportData;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText ipEdt, portEdt, coNoEdt;
    public List<Item> allItemsList;
    private List<CustomerInfo> allCustomers;
    private List<User> allUsers;
    TextInputEditText unameEdt, passEdt;
    TextInputLayout unameTextField, passTextField;
    CircularProgressButton loginBtn;
    TextView errorMsg;
    //    ImageView settingsIc;
    TextInputLayout uTypeTextField;
    AutoCompleteTextView uTypeEdt;
    LinearLayout request_ip_;
    GeneralMethod generalMethod;
    public final static String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    public final static String IP_PREF = "IP_Address";
    public final static String PORT_PREF = "IP_Port";
    public final static String CONO_PREF = "Company_No";

    AppDatabase appDatabase;

    String ipAddress, ipPort, coNo;

    ImportData importData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.loginTitle));

        init();

        if (!checkIpSettings())
            showSettingsDialog();


        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginBtn.startAnimation();

                if (!checkIpSettings()) {
                    showSettingsDialog();
                    loginBtn.revertAnimation();
                } else
                    checkUnameAndPass();

            }
        });

        unameEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                errorMsg.setVisibility(View.INVISIBLE);
                unameTextField.setError(null);

            }
        });

        passEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                errorMsg.setVisibility(View.INVISIBLE);
                passTextField.setError(null);

            }
        });

        request_ip_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSettingsDialog();

            }
        });

    }

    private boolean checkIpSettings() {

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        ipAddress = sharedPref.getString(IP_PREF, "");
        ipPort = sharedPref.getString(PORT_PREF, "");
        coNo = sharedPref.getString(CONO_PREF, "");

        Log.e("IP_PREF", ipAddress + "");
        Log.e("PORT_PREF", ipPort);
        Log.e("CONO_PREF", coNo);

        return !(ipAddress + "").trim().equals("") &&
                !(ipPort + "").trim().equals("") &&
                !(coNo + "").trim().equals("");

    }

    void init() {
        appDatabase = AppDatabase.getInstanceDatabase(LoginActivity.this);
        generalMethod = new GeneralMethod(LoginActivity.this);

        importData = new ImportData(this);
        appDatabase = AppDatabase.getInstanceDatabase(this);
        allItemsList = new ArrayList<>();
        allCustomers = new ArrayList<>();
        allUsers = new ArrayList<>();

        loginBtn = findViewById(R.id.loginBtn);
        unameEdt = findViewById(R.id.unameEdt);
        passEdt = findViewById(R.id.passEdt);
        unameTextField = findViewById(R.id.unameTextField);
        passTextField = findViewById(R.id.passTextField);
        errorMsg = findViewById(R.id.errorMsg);
//        settingsIc = findViewById(R.id.settingsIc);
        uTypeEdt = findViewById(R.id.uTypeEdt);
        uTypeTextField = findViewById(R.id.uTypeTextField);
        request_ip_ = findViewById(R.id.request_ip_);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.user_type));

        uTypeEdt.setAdapter(adapter);
        uTypeEdt.setText(getResources().getStringArray(R.array.user_type)[1], false);

    }

    void checkUnameAndPass() {

        String uname = unameEdt.getText().toString().trim() + "";
        String pass = passEdt.getText().toString().trim() + "";
        int uType = uTypeEdt.getText().toString().equals(getResources().getStringArray(R.array.user_type)[0]) ? 1 : 0;

        allUsers = appDatabase.usersDao().getAllUsers();
        boolean valid = false;
        int i;

        if (allUsers.size() != 0) {

            if (!uname.equals("")) {

                if (!pass.equals("")) {

                    for (i = 0; i < allUsers.size(); i++) {

                        if (uname.equals(allUsers.get(i).getUserName()) &&
                                pass.equals(allUsers.get(i).getUserPassword()) &&
                                allUsers.get(i).getUserType() == uType) {

                            valid = true;
                            break;

                        }
                    }

                    if (valid) {

                        errorMsg.setVisibility(View.INVISIBLE);
                        Toast.makeText(LoginActivity.this, "SUCCESS LOGIN!", Toast.LENGTH_SHORT).show();

                        addUserLogs(i);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(intent);

                    } else {

                        errorMsg.setVisibility(View.VISIBLE);

                    }
                    loginBtn.revertAnimation();


                } else {

                    loginBtn.revertAnimation();
                    passTextField.setError(getString(R.string.required));
//                passEdt.setError("");

                }

            } else {

                loginBtn.revertAnimation();
                unameTextField.setError(getString(R.string.required));

            }

        } else {

            Toast.makeText(this, "No Saved Users Found !", Toast.LENGTH_SHORT).show();
            loginBtn.revertAnimation();

//            /** TODO Delete This **/
//            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(intent);

        }


    }

    void showSettingsDialog() {

        final Dialog ip_settings_dialog = new Dialog(LoginActivity.this);
        ip_settings_dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ip_settings_dialog.setCancelable(false);
        ip_settings_dialog.setContentView(R.layout.ip_settings_dialog);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(ip_settings_dialog.getWindow().getAttributes());
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.19);
        ip_settings_dialog.getWindow().setAttributes(lp);

        ip_settings_dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        ip_settings_dialog.show();


        ipEdt = ip_settings_dialog.findViewById(R.id.ipEdt);
        portEdt = ip_settings_dialog.findViewById(R.id.portEdt);
        coNoEdt = ip_settings_dialog.findViewById(R.id.coNoEdt);
        TextInputLayout textInputIpAddress, textInputPort, textInputCoNo;
        textInputIpAddress = ip_settings_dialog.findViewById(R.id.textInputIpAddress);
        textInputPort = ip_settings_dialog.findViewById(R.id.textInputPort);
        textInputCoNo = ip_settings_dialog.findViewById(R.id.textInputCoNo);

        Button okBtn, cancelBtn;
        okBtn = ip_settings_dialog.findViewById(R.id.okBtn);
        cancelBtn = ip_settings_dialog.findViewById(R.id.cancelBtn);

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        ipEdt.setText(sharedPref.getString(IP_PREF, ""));
        portEdt.setText(sharedPref.getString(PORT_PREF, ""));
        coNoEdt.setText(sharedPref.getString(CONO_PREF, ""));

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ip_settings_dialog.dismiss();

            }
        });

        ipEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputIpAddress.setError(null);

            }
        });

        portEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputPort.setError(null);

            }
        });

        coNoEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputCoNo.setError(null);

            }
        });


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String ipAddress = ipEdt.getText().toString().trim();
                String port = portEdt.getText().toString().trim();
                String coNo = coNoEdt.getText().toString().trim();

                if (!ipAddress.equals("")) {

                    if (!port.equals("")) {

                        if (!coNo.equals("")) {

                            SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
                            editor.putString(IP_PREF, ipAddress);
                            editor.putString(PORT_PREF, port);
                            editor.putString(CONO_PREF, coNo);
                            editor.apply();

                            appDatabase.itemsDao().deleteAll();
                            appDatabase.customersDao().deleteAll();
                            appDatabase.usersDao().deleteAll();

                            allItemsList.clear();
                            allCustomers.clear();
                            allUsers.clear();

//                            importData.getAllItems();
                            importData.getAllItems(new ImportData.GetItemsCallBack() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {

                                        JSONArray itemsArray = response.getJSONArray("Items_Master");

                                        for (int i = 0; i < itemsArray.length(); i++) {

                                            Item item = new Item();
                                            item.setItem_Name(itemsArray.getJSONObject(i).getString("NAME"));
                                            item.setItemNCode(itemsArray.getJSONObject(i).getString("BARCODE"));
                                            item.setItemOCode(itemsArray.getJSONObject(i).getString("ITEMNO"));
                                            item.setImagePath(itemsArray.getJSONObject(i).getString("ITEMPICSPATH"));
                                            item.setItemKind(itemsArray.getJSONObject(i).getString("ItemK"));
                                            item.setPrice(Double.parseDouble(itemsArray.getJSONObject(i).getString("MINPRICE")));
                                            item.setCategoryId(itemsArray.getJSONObject(i).getString("CATEOGRYID"));
                                            item.setTax(Double.parseDouble(itemsArray.getJSONObject(i).getString("TAXPERC")));
                                            item.setTaxPercent(Double.parseDouble(itemsArray.getJSONObject(i).getString("TAXPERC")) / 100);
                                            allItemsList.add(item);

                                        }

                                        appDatabase.itemsDao().addAll(allItemsList);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    importData.getAllCustomers(new ImportData.GetCustomersCallBack() {
                                        @Override
                                        public void onResponse(JSONArray response) {

                                            for (int i = 0; i < response.length(); i++) {

                                                try {

                                                    allCustomers.add(new CustomerInfo(
                                                            response.getJSONObject(i).getString("CUSTID"),
                                                            response.getJSONObject(i).getString("CUSTNAME"),
                                                            response.getJSONObject(i).getString("MOBILE"),
                                                            1));

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            appDatabase.customersDao().addAll(allCustomers);

                                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    GeneralMethod.showSweetDialog(LoginActivity.this, 1, "Saved", null);

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


                                        }

                                        @Override
                                        public void onError(String error) {

                                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    GeneralMethod.showSweetDialog(LoginActivity.this, 1, "Saved", null);

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


                                        }
                                    }, ipAddress, port, coNo);

                                }

                                @Override
                                public void onError(String error) {

                                    importData.getAllCustomers(new ImportData.GetCustomersCallBack() {
                                        @Override
                                        public void onResponse(JSONArray response) {

                                            for (int i = 0; i < response.length(); i++) {

                                                try {

                                                    allCustomers.add(new CustomerInfo(
                                                            response.getJSONObject(i).getString("CUSTID"),
                                                            response.getJSONObject(i).getString("CUSTNAME"),
                                                            response.getJSONObject(i).getString("MOBILE"),
                                                            1));

                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }

                                            }

                                            appDatabase.customersDao().addAll(allCustomers);

                                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    GeneralMethod.showSweetDialog(LoginActivity.this, 1, "Saved", null);

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


                                        }

                                        @Override
                                        public void onError(String error) {

                                            importData.getAllUsers(new ImportData.GetUsersCallBack() {
                                                @Override
                                                public void onResponse(JSONArray response) {

                                                    GeneralMethod.showSweetDialog(LoginActivity.this, 1, "Saved", null);

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


                                        }
                                    }, ipAddress, port, coNo);


                                }
                            }, ipAddress, port, coNo);


                            ip_settings_dialog.dismiss();

                        } else {

                            textInputCoNo.setError(getString(R.string.required));


                        }

                    } else {

                        textInputPort.setError(getString(R.string.required));


                    }

                } else {

                    textInputIpAddress.setError(getString(R.string.required));


                }

            }
        });

    }

    void addUserLogs(int index) {
        UserLogs userLogs = new UserLogs();
        userLogs.setUserID(allUsers.get(index).getUserId() + "");
        userLogs.setUserName(allUsers.get(index).getUserName() + "");
        userLogs.setPassword(passEdt.getText().toString().trim());
        userLogs.setDate(generalMethod.getCurentTimeDate(1));
        userLogs.setTime(generalMethod.getCurentTimeDate(2));
        appDatabase.userLogsDao().insertUser(userLogs);
    }
    @Override
    public void onBackPressed() {

    }
}