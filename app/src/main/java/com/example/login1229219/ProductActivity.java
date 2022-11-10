package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {
    TextView tv_author, tv_price, tv_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tv_author = findViewById(R.id.tv_author);
        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_author.setText(extras.getString("author"));
            tv_price.setText(extras.getString("price"));
            tv_name.setText(extras.getString("title"));
        }


    }
}