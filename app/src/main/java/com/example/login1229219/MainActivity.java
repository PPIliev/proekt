package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button b_login, b_register;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();

        if (sPreferences.contains("isLoggedInAsNormalUser")) {
            Intent i = Login.goToDashboardNormal(getApplicationContext());
            startActivity(i);
        } else if (sPreferences.contains("isLoggedInAsOtherUser")) {
            Intent i = Login.goToDashboardOther(getApplicationContext());
            startActivity(i);
        }

        b_login = findViewById(R.id.b_login);
        b_register = findViewById(R.id.b_register);



        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLogin();
            }
        });


    }

    public void goLogin() {
        Intent i = new Intent(getApplicationContext(), Login.class);
        startActivity(i);
    }

    public void goRegister() {
        Intent i = new Intent(getApplicationContext(), Register.class);
        startActivity(i);
    }
}