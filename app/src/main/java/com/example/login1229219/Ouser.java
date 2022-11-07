package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ouser extends AppCompatActivity {
    Button b_logout;
    SharedPreferences sPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouser);

        sPreferences = getSharedPreferences("My preferences", MODE_PRIVATE);
        editor = sPreferences.edit();

        b_logout = findViewById(R.id.b_logout);

        b_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                SharedPreferences.Editor editor = getSharedPreferences("sharedPreferencesOther", MODE_PRIVATE).edit();
////                editor.putString("password", "");
////                editor.putString("email", "");
//                editor.putBoolean("isLoggedInAsOtherUser", false);
//                editor.apply();
//
//                Intent intent = new Intent(Ouser.this, MainActivity.class);
//                intent.putExtra("finish", true);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//
//                finish();
                editor.clear();
                editor.commit();
                goToMain();
            }
        });

    }

    public void goToMain() {
        Intent i = new Intent(Ouser.this, MainActivity.class);
        startActivity(i);
    }

}