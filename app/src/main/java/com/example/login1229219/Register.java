package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;

public class Register extends AppCompatActivity {
    Button b_register;
    EditText et_password2, et_password, et_username, et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b_register = findViewById(R.id.b_register);
        et_password = findViewById(R.id.et_password);
        et_password2 = findViewById(R.id.et_password2);
        et_username = findViewById(R.id.et_username);
        et_email = findViewById(R.id.et_email);


    }
}