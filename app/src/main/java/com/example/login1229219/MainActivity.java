package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText et_username, et_password;
    Button b_login, b_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        b_login = findViewById(R.id.b_login);
        b_register = findViewById(R.id.b_register);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegister();
            }
        });



    }

    public void goRegister() {
        Intent i = new Intent(getApplicationContext(), Register.class);
//        i.putExtra("info",)
        startActivity(i);
    }
}