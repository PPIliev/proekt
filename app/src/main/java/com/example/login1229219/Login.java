package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {
    Button b_login;
    EditText et_username, et_password;

    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b_login = findViewById(R.id.b_login);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();


        if (sPreferences.contains("isLoggedInAsNormalUser")) {
            goToDashboardNormal();
        } else if (sPreferences.contains("isLoggedInAsOtherUser")) {
            goToDashboardOther();
        }


        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                Dbhelper dbhelper = new Dbhelper(getApplicationContext());

                if (dbhelper.checkUserType(username, password) == 0) {
                    Toast.makeText(Login.this, "Error!", Toast.LENGTH_SHORT).show();
                } else if (dbhelper.checkUserType(username, password) == 1){
                    editor.putBoolean("isLoggedInAsNormalUser", true);
                    editor.commit();
                    goToDashboardNormal();
                } else if (dbhelper.checkUserType(username, password) == 2) {
                    editor.putBoolean("isLoggedInAsOtherUser", true);
                    editor.commit();
                    goToDashboardOther();
                }

            }
        });
    }



    public void goToDashboardNormal() {
        setContentView(R.layout.activity_nuser);
        Intent i = new Intent(Login.this,Nuser.class);
        startActivity(i);

    }

    public void goToDashboardOther() {
        setContentView(R.layout.activity_ouser);
        Intent i = new Intent(Login.this,Ouser.class);
        startActivity(i);

    }

}