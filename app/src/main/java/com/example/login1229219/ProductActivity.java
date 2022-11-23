package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login1229219.Helpers.ProductsHelper;

public class ProductActivity extends AppCompatActivity {
    TextView tv_author, tv_price, tv_name;
    ImageView iv_productImgOne, iv_productImgTwo;
    ProductsHelper pHelper = new ProductsHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        tv_author = findViewById(R.id.tv_author);
        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);
        iv_productImgOne = findViewById(R.id.iv_productImgOne);
        iv_productImgTwo = findViewById(R.id.iv_productImgTwo);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_author.setText(extras.getString("author"));
            tv_price.setText(extras.getString("price"));
            tv_name.setText(extras.getString("title"));
            iv_productImgOne.setImageBitmap(pHelper.stringToBitmap(extras.getString("image")));
            iv_productImgTwo.setImageBitmap(pHelper.stringToBitmap(extras.getString("imageTwo")));
        }


    }
}