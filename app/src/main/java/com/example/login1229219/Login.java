package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login1229219.Dashboards.Nuser;
import com.example.login1229219.Dashboards.Ouser;
import com.example.login1229219.DataBases.Dbhelper;

public class Login extends AppCompatActivity {
    Button b_login;
    EditText et_username, et_password;
    TextView tv_registered;

    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        b_login = findViewById(R.id.b_login);
        tv_registered = findViewById(R.id.tv_registered);
        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);

        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();


        if (sPreferences.contains("isLoggedInAsNormalUser")) {
            Intent i = goToDashboardNormal(getApplicationContext());
            startActivity(i);
        } else if (sPreferences.contains("isLoggedInAsOtherUser")) {
//            editor.putString("author", et_username.getText().toString());
            Intent i = goToDashboardOther(getApplicationContext());
            startActivity(i);
        }

        //Get error message from Login activity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_registered.setText(extras.getString("registered"));
            tv_registered.setVisibility(View.VISIBLE);
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
                    editor.putString("author", et_username.getText().toString());
                    editor.commit();
                    Intent i = goToDashboardNormal(getApplicationContext());
                    i.putExtra("username", et_username.getText().toString());
                    startActivity(i);
                } else if (dbhelper.checkUserType(username, password) == 2) {
                    editor.putBoolean("isLoggedInAsOtherUser", true);
                    editor.putString("author", et_username.getText().toString());
                    editor.commit();
                    Intent i = goToDashboardOther(getApplicationContext());
                    i.putExtra("username", et_username.getText().toString());
                    startActivity(i);
                }

            }
        });
    }



    public static Intent goToDashboardNormal(Context context) {
        return new Intent(context, Nuser.class);

    }

    public static Intent goToDashboardOther(Context context) {
        return new Intent(context, Ouser.class);

    }



}