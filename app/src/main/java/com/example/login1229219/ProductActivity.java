package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.login1229219.Adapters.SlidersAdapter;
import com.example.login1229219.DataBases.Dbhelper;
import com.example.login1229219.Helpers.ProductsHelper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ProductActivity extends AppCompatActivity {
    TextView tv_author, tv_price, tv_name;
    ProductsHelper pHelper = new ProductsHelper();
    SliderView sliderView;
    Bitmap imageOne, imageTwo;
    Dbhelper db;
    Button b_phoneNumber, b_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sliderView = findViewById(R.id.imageslider);
        tv_author = findViewById(R.id.tv_author);
        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);
        b_phoneNumber = findViewById(R.id.b_phoneNumber);
        b_email = findViewById(R.id.b_email);

        db = new Dbhelper(getApplicationContext());


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_author.setText(extras.getString("author"));
            tv_price.setText(extras.getString("price"));
            tv_name.setText(extras.getString("title"));
            int value = db.getContactsPhone(tv_author.getText().toString());
            b_phoneNumber.setText(String.valueOf(value));
            b_email.setText(db.getContactsEmail(tv_author.getText().toString()));


            imageOne = pHelper.stringToBitmap(extras.getString("image"));
            imageTwo = pHelper.stringToBitmap(extras.getString("imageTwo"));
        }


        Bitmap[] images = {imageOne, imageTwo};
        SlidersAdapter sliderAdapter = new SlidersAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);

        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);


        b_email.setOnClickListener(view -> {
            try {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + b_email.getText().toString()));
                startActivity(i);
            } catch (ActivityNotFoundException e) {

            }
        });

        b_phoneNumber.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + b_phoneNumber.getText().toString()));
//            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
//            }
        });
    }
}