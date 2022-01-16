package com.falcons.buildingstore;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Objects;

import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    EditText unameEdt, passEdt;
    CircularProgressButton loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(getString(R.string.loginTitle));

        init();

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "Hellooo!", Toast.LENGTH_SHORT).show();
                loginBtn.startAnimation();
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

                loginBtn.revertAnimation();

            }
        });

    }

    void init() {

        loginBtn = findViewById(R.id.loginBtn);
        unameEdt = findViewById(R.id.unameEdt);
        passEdt = findViewById(R.id.passEdt);

    }


}