package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.login1229219.Adapters.SlidersAdapter;
import com.example.login1229219.Helpers.ProductsHelper;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

public class ProductActivity extends AppCompatActivity {
    TextView tv_author, tv_price, tv_name;
//    ImageView iv_productImgOne, iv_productImgTwo;
    ProductsHelper pHelper = new ProductsHelper();
    SliderView sliderView;
    Bitmap imageOne, imageTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        sliderView = findViewById(R.id.imageslider);
        tv_author = findViewById(R.id.tv_author);
        tv_price = findViewById(R.id.tv_price);
        tv_name = findViewById(R.id.tv_name);
//        iv_productImgOne = findViewById(R.id.iv_productImgOne);
//        iv_productImgTwo = findViewById(R.id.iv_productImgTwo);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            tv_author.setText(extras.getString("author"));
            tv_price.setText(extras.getString("price"));
            tv_name.setText(extras.getString("title"));
//            iv_productImgOne.setImageBitmap(pHelper.stringToBitmap(extras.getString("image")));
//            iv_productImgTwo.setImageBitmap(pHelper.stringToBitmap(extras.getString("imageTwo")));
            imageOne = pHelper.stringToBitmap(extras.getString("image"));
            imageTwo = pHelper.stringToBitmap(extras.getString("imageTwo"));
        }
//        Bitmap one = ((BitmapDrawable)iv_productImgOne.getDrawable()).getBitmap();
//        Bitmap two = ((BitmapDrawable)iv_productImgTwo.getDrawable()).getBitmap();

        Bitmap[] images = {imageOne, imageTwo};
        SlidersAdapter sliderAdapter = new SlidersAdapter(images);
        sliderView.setSliderAdapter(sliderAdapter);
//        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);

        sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
//        sliderView.startAutoCycle();




    }
}