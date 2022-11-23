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

import com.example.login1229219.Helpers.NavigationHelper;

public class MainActivity extends AppCompatActivity {
    Button b_login, b_register;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;
    NavigationHelper nHelper = new NavigationHelper();

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
                nHelper.goRegister(getApplicationContext());
            }
        });

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nHelper.goLogin(getApplicationContext());
            }
        });


    }
}