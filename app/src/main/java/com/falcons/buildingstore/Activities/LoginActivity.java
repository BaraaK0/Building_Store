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
import com.falcons.buildingstore.Database.Entities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.UserLogs;
import com.falcons.buildingstore.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText unameEdt, passEdt;
    TextInputLayout unameTextField, passTextField;
    CircularProgressButton loginBtn;
    TextView errorMsg;
//    ImageView settingsIc;
    AutoCompleteTextView uTypeEdt;
    LinearLayout request_ip_;
GeneralMethod generalMethod;
    public final static String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    public final static String IP_PREF = "IP_Address";
    public final static String PORT_PREF = "IP_Port";
    public final static String CONO_PREF = "Company_No";
  String ipAddress, ipPort, coNo;
AppDatabase  appDatabase;
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
                }
                else
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
        appDatabase=AppDatabase.getInstanceDatabase(LoginActivity.this);
        generalMethod=new GeneralMethod(LoginActivity.this);
        loginBtn = findViewById(R.id.loginBtn);
        unameEdt = findViewById(R.id.unameEdt);
        passEdt = findViewById(R.id.passEdt);
        unameTextField = findViewById(R.id.unameTextField);
        passTextField = findViewById(R.id.passTextField);
        errorMsg = findViewById(R.id.errorMsg);
//        settingsIc = findViewById(R.id.settingsIc);
        uTypeEdt = findViewById(R.id.uTypeEdt);
        request_ip_ = findViewById(R.id.request_ip_);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.user_type));

        uTypeEdt.setAdapter(adapter);

    }

    void checkUnameAndPass() {

        String uname = unameEdt.getText().toString().trim() + "";
        String pass = passEdt.getText().toString().trim() + "";

        if (!uname.equals("")) {

            if (!pass.equals("")) {

                if (uname.equals("1") && pass.equals("1234")) {

                    loginBtn.revertAnimation();
                    errorMsg.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this, "SUCCESS LOGIN!", Toast.LENGTH_SHORT).show();


                    addUserLogs();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {

                    loginBtn.revertAnimation();
                    errorMsg.setVisibility(View.VISIBLE);

                }

            } else {

                loginBtn.revertAnimation();
                passTextField.setError(getString(R.string.required));
//                passEdt.setError("");

            }

        } else {

            loginBtn.revertAnimation();
            unameTextField.setError(getString(R.string.required));

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

        TextInputEditText ipEdt, portEdt, coNoEdt;
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
  void  addUserLogs(){
      UserLogs userLogs=new UserLogs();
      userLogs.setUserID(unameEdt.getText().toString().trim());
      userLogs.setPassword(passEdt.getText().toString().trim());
      userLogs.setDate(generalMethod.getCurentTimeDate(1));
      userLogs.setTime(generalMethod.getCurentTimeDate(2));
      appDatabase.userLogsDao().insertUser(userLogs);
  }
}