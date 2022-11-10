package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    TextView tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tv_title = findViewById(R.id.tv_title);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_title.setText(extras.getString("title"));
        }


    }
}