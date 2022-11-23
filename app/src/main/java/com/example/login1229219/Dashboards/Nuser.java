package com.example.login1229219.Dashboards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.MainActivity;
import com.example.login1229219.R;

public class Nuser extends AppCompatActivity {
    Button b_logout;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;
    TextView tv_hello;
    NavigationHelper nHelper = new NavigationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuser);
        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();

        b_logout = findViewById(R.id.b_logoutNormal);
        tv_hello = findViewById(R.id.tv_hello);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String user = extras.getString("username");
            tv_hello.setText("Hello " + user + " wellcome to the dashboard!");
            tv_hello.setVisibility(View.VISIBLE);
        }

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.commit();
                nHelper.goToMain(getApplicationContext());
            }
        });

    }

}