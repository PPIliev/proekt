package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Ouser extends AppCompatActivity {
    Button b_logout, b_yourProducts;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;
    TextView tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouser);

        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();

        b_logout = findViewById(R.id.b_logout);
        b_yourProducts = findViewById(R.id.b_yourProducts);
        tv_user = findViewById(R.id.tv_user);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String user = extras.getString("username");
            tv_user.setText("Hello " + user + " wellcome to the dashboard!");
            tv_user.setVisibility(View.VISIBLE);
        }

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                goToMain();
            }
        });

        b_yourProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToProducts();
            }
        });

    }

    public void goToMain() {
        Intent i = new Intent(Ouser.this, MainActivity.class);
        startActivity(i);
    }

    public void goToProducts() {
        Intent i = new Intent(Ouser.this, ProductsList.class);
        startActivity(i);
    }

}