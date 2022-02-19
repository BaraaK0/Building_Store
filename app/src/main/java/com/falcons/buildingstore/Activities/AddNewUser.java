package com.falcons.buildingstore.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.falcons.buildingstore.Database.AppDatabase;
import com.falcons.buildingstore.Database.Entities.GeneralMethod;
import com.falcons.buildingstore.Database.Entities.User;
import com.falcons.buildingstore.R;
import br.com.simplepass.loadingbutton.customViews.CircularProgressButton;
public class AddNewUser extends AppCompatActivity {
 EditText username,passEdt;
 Spinner usertype;
 RadioGroup dis_Per;
   RadioButton RB_yes,RB_no;
    CircularProgressButton adduser;
    AppDatabase appDatabase;
    GeneralMethod generalMethod;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_user);
        init();

        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add_new_User();
            }
        });
    }
    void init (){
        generalMethod=new GeneralMethod(AddNewUser.this);

        username=findViewById(R.id.username);
        passEdt=findViewById(R.id.passEdt);
        usertype=findViewById(R.id.usertype);
        dis_Per=findViewById(R.id.dis_Per);
        RB_yes=findViewById(R.id.RB_yes);
        RB_no=findViewById(R.id.RB_no);
        adduser=findViewById(R.id.adduser);
        appDatabase=AppDatabase.getInstanceDatabase(AddNewUser.this);
    }
    void Add_new_User(){

        if(!username.getText().toString().trim().equals("")
        &&!passEdt.getText().toString().trim().equals("")) {
            User user=new User();
            user.setUserName(username.getText().toString());
            user.setUserPassword(passEdt.getText().toString());
            if (RB_yes.isChecked() == true)
                user.setDiscPermission("1");
            else
                user.setDiscPermission("0");
            appDatabase.usersDao().insertUser(user);
            generalMethod.showSweetDialog(this, 1, this.getResources().getString(R.string.savedSuccsesfule), "");
            username.setText("");
            passEdt.setText("");
        }
        else if (username.getText().toString().trim().equals(""))
            username.setError(getResources().getString(R.string.Empty));
        else if (passEdt.getText().toString().trim().equals(""))
            username.setError(getResources().getString(R.string.Empty));


    }
}