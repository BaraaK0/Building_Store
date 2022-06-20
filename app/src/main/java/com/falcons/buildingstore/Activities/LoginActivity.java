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
import android.view.Gravity;
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
import com.falcons.buildingstore.Database.Entities.Item_Unit_Details;
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
import java.util.Locale;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText ipEdt, portEdt, coNoEdt,maxVochEdt;
   ImageView editVochNo,saveVochNo;
    public List<Item> allItemsList;
    public List<Item_Unit_Details> allUnitDetails;
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
    public final static String maxVoch_PREF = "maxVoch";
    AppDatabase appDatabase;
    EditText  editPassword;
    String ipAddress, ipPort, coNo,Max_Voch;

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
        Max_Voch = sharedPref.getString(maxVoch_PREF, "");
        Log.e("IP_PREF", ipAddress + "");
        Log.e("PORT_PREF", ipPort);
        Log.e("CONO_PREF", coNo);
        Log.e("CONO_PREF",  Max_Voch);
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
        allUnitDetails = new ArrayList<>();

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

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);



    }

    void checkUnameAndPass() {

        String uname = unameEdt.getText().toString().trim().toLowerCase(Locale.ROOT) + "";
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
        editVochNo= ip_settings_dialog.findViewById(R.id.editVochNo);

        int MaxVo= appDatabase.ordersMasterDao(). getLastVoherNo();




        editVochNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                    final Dialog dialog = new Dialog(LoginActivity.this);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.passwordsettings);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.copyFrom(dialog.getWindow().getAttributes());

                    lp.gravity = Gravity.CENTER;
                    dialog.getWindow().setAttributes(lp);
                    Button saveButton = (Button) dialog.findViewById(R.id.saveButton);
//                    TextView cancelButton = (TextView) dialog.findViewById(R.id.cancel);

              editPassword = (EditText) dialog.findViewById(R.id.passowrdEdit);


                    saveButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!editPassword.getText().toString().equals("")) {
                                if(editPassword.getText().toString().trim().equals("1234"))
                                {


                                        maxVochEdt.setEnabled(true);
                                        maxVochEdt.requestFocus();
                                        dialog.dismiss();



                                }else
                                {
                                    editPassword.setError("password not correct");
                                }
                            } else {
                                editPassword.setError("Required");
                            }


                        }
                    });
//
//                    cancelButton.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            dialog.dismiss();
//                        }
//                    });
                    dialog.show();



            }
        });
        ipEdt = ip_settings_dialog.findViewById(R.id.ipEdt);
        portEdt = ip_settings_dialog.findViewById(R.id.portEdt);
        coNoEdt = ip_settings_dialog.findViewById(R.id.coNoEdt);
        maxVochEdt= ip_settings_dialog.findViewById(R.id.maxVoch);
        maxVochEdt.setEnabled(false);
        TextInputLayout textInputIpAddress, textInputPort, textInputCoNo,textInputMaxVoch;
        textInputIpAddress = ip_settings_dialog.findViewById(R.id.textInputIpAddress);
        textInputPort = ip_settings_dialog.findViewById(R.id.textInputPort);
        textInputCoNo = ip_settings_dialog.findViewById(R.id.textInputCoNo);
        textInputMaxVoch = ip_settings_dialog.findViewById(R.id.textInputmaxVoch);
        Button okBtn, cancelBtn;
        okBtn = ip_settings_dialog.findViewById(R.id.okBtn);
        cancelBtn = ip_settings_dialog.findViewById(R.id.cancelBtn);
        textInputMaxVoch.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!maxVochEdt.getText().toString().trim().equals(""))
                {
                    Log.e("MaxVo==",MaxVo+"");

                    if(Integer.parseInt(maxVochEdt.getText().toString().trim())>MaxVo)
                {
                    maxVochEdt.setEnabled(false);
                    SharedPreferences.Editor editor = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE).edit();
                    editor.putString(maxVoch_PREF,  maxVochEdt.getText().toString().trim());
                    editor.apply();
                }
                else
                {
                    maxVochEdt.setText("");
                  if(MaxVo!=0)
                      generalMethod.showSweetDialog(LoginActivity.this,0,"",getResources().getString(R.string.MaxVoMsg1)+" "+MaxVo+getResources().getString(R.string.MaxVoMsg2)+(MaxVo+1)+"  " +getResources().getString(R.string.MaxVoMsg3));
                   else   generalMethod.showSweetDialog(LoginActivity.this,0,"",getResources().getString(R.string.MaxVoMsg2)+(MaxVo+1)+"  " +getResources().getString(R.string.MaxVoMsg3));

                    maxVochEdt.setError("");
                }

            }}
        });
        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        ipEdt.setText(sharedPref.getString(IP_PREF, ""));
        portEdt.setText(sharedPref.getString(PORT_PREF, ""));
        coNoEdt.setText(sharedPref.getString(CONO_PREF, ""));
        maxVochEdt.setText(sharedPref.getString(maxVoch_PREF, ""));
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
        maxVochEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                textInputMaxVoch.setError(null);

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
                            appDatabase.itemUnitsDao().deleteAll();

                            allItemsList.clear();
                            allUnitDetails.clear();
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
                                            item.setImagePath(itemsArray.getJSONObject(i).getString("ISAPIPIC"));
                                            item.setItemKind(itemsArray.getJSONObject(i).getString("ItemK"));
                                            item.setPrice(Double.parseDouble(itemsArray.getJSONObject(i).getString("F_D")));
                                            item.setCategoryId(itemsArray.getJSONObject(i).getString("CATEOGRYID"));
                                            item.setTax(Double.parseDouble(itemsArray.getJSONObject(i).getString("TAXPERC")));
                                            item.setTaxPercent(Double.parseDouble(itemsArray.getJSONObject(i).getString("TAXPERC")) / 100);
                                            allItemsList.add(item);

                                        }

                                        appDatabase.itemsDao().addAll(allItemsList);

                                        JSONArray unitsArray = response.getJSONArray("Item_Unit_Details");

                                        for (int i = 0; i < unitsArray.length(); i++) {

                                            Item_Unit_Details itemUnitDetails = new Item_Unit_Details();
                                            itemUnitDetails.setCompanyNo(unitsArray.getJSONObject(i).getString("COMAPNYNO"));
                                            itemUnitDetails.setItemNo(unitsArray.getJSONObject(i).getString("ITEMNO"));
                                            itemUnitDetails.setUnitId(unitsArray.getJSONObject(i).getString("UNITID"));
                                            itemUnitDetails.setConvRate(Double.parseDouble(unitsArray.getJSONObject(i).getString("CONVRATE")));

                                            allUnitDetails.add(itemUnitDetails);

                                        }

                                        appDatabase.itemUnitsDao().addAll(allUnitDetails);

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
                                                                 response.getJSONObject(i).getString("ACCNAME").toLowerCase(Locale.ROOT),
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

                                            if (!((error + "").contains("SSLHandshakeException") || (error + "").equals("null") ||
                                                    (error + "").contains("ConnectException") || (error + "").contains("NoRouteToHostException"))) {

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


                                        }
                                    }, ipAddress, port, coNo);

                                }

                                @Override
                                public void onError(String error) {

                                    if (!((error + "").contains("SSLHandshakeException") || (error + "").equals("null") ||
                                            (error + "").contains("ConnectException") || (error + "").contains("NoRouteToHostException"))) {

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

                                                if (!((error + "").contains("SSLHandshakeException") || (error + "").equals("null") ||
                                                        (error + "").contains("ConnectException") || (error + "").contains("NoRouteToHostException"))) {

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

                                            }
                                        }, ipAddress, port, coNo);
                                    }

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