package com.example.login1229219.Dashboards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.R;

public class Ouser extends AppCompatActivity {
    Button b_logout, b_yourProducts, b_userProducts;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;
    TextView tv_user;
    NavigationHelper nHelper = new NavigationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouser);

        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();
        String userName = sPreferences.getString("author", "");

        b_logout = findViewById(R.id.b_logout);
        b_yourProducts = findViewById(R.id.b_yourProducts);
        b_userProducts = findViewById(R.id.b_userProducts);
        tv_user = findViewById(R.id.tv_user);

        tv_user.setText(userName);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
//            String user = extras.getString("username");
//            tv_user.setText(user);
            tv_user.setVisibility(View.VISIBLE);
        }

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                nHelper.goToMain(getApplicationContext());
            }
        });

        b_yourProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nHelper.goToProducts(getApplicationContext(), tv_user);
            }
        });

        b_userProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nHelper.goToUserProducts(getApplicationContext(), tv_user);
            }
        });


    }

}