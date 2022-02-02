package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.falcons.buildingstore.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText unameEdt, passEdt;
    TextInputLayout unameTextField, passTextField;
    CircularProgressButton loginBtn;
    TextView errorMsg;
    ImageView settingsIc;

    public final static String SETTINGS_PREFERENCES = "SETTINGS_PREFERENCES";
    public final static String IP_PREF = "IP_Address";
    public final static String PORT_PREF = "IP_Port";
    public final static String CONO_PREF = "Company_No";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.loginTitle));

        init();

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);
        String ipAddress = sharedPref.getString(IP_PREF, "");

        Log.e("IP_PREF", ipAddress + "");
        Log.e("PORT_PREF", sharedPref.getInt(PORT_PREF, 0) + "");
        Log.e("CONO_PREF", sharedPref.getInt(CONO_PREF, 0) + "");

        if ((ipAddress + "").trim().equals("")) {

            showSettingsDialog();

        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginBtn.startAnimation();

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

        settingsIc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showSettingsDialog();

            }
        });

    }

    void init() {

        loginBtn = findViewById(R.id.loginBtn);
        unameEdt = findViewById(R.id.unameEdt);
        passEdt = findViewById(R.id.passEdt);
        unameTextField = findViewById(R.id.unameTextField);
        passTextField = findViewById(R.id.passTextField);
        errorMsg = findViewById(R.id.errorMsg);
        settingsIc = findViewById(R.id.settingsIc);

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
        lp.width = (int) (getResources().getDisplayMetrics().widthPixels / 1.15);
        ip_settings_dialog.getWindow().setAttributes(lp);
        ip_settings_dialog.show();

        EditText ipEdt, portEdt, coNoEdt;
        ipEdt = ip_settings_dialog.findViewById(R.id.ipEdt);
        portEdt = ip_settings_dialog.findViewById(R.id.portEdt);
        coNoEdt = ip_settings_dialog.findViewById(R.id.coNoEdt);

        Button okBtn, cancelBtn;
        okBtn = ip_settings_dialog.findViewById(R.id.okBtn);
        cancelBtn = ip_settings_dialog.findViewById(R.id.cancelBtn);

        SharedPreferences sharedPref = getSharedPreferences(SETTINGS_PREFERENCES, MODE_PRIVATE);

        ipEdt.setText(sharedPref.getString(IP_PREF, ""));
        portEdt.setText(sharedPref.getInt(PORT_PREF, 0) + "");
        coNoEdt.setText(sharedPref.getInt(CONO_PREF, 0) + "");

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ip_settings_dialog.dismiss();

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
                            editor.putInt(PORT_PREF, Integer.parseInt(port));
                            editor.putInt(CONO_PREF, Integer.parseInt(coNo));
                            editor.apply();

                            ip_settings_dialog.dismiss();

                        } else {

                            coNoEdt.setError("");


                        }

                    } else {

                        portEdt.setError("");


                    }

                } else {

                    ipEdt.setError("");


                }

            }
        });

    }

}