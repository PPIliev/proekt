package com.example.login1229219;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.login1229219.DataBases.MyDatabaseHelper;
import com.example.login1229219.Helpers.ProductsHelper;

import java.io.IOException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    EditText et_title, et_price;
    ImageView iv_image, iv_secondImage;
    Button b_gallery, b_camera, b_firstImg, b_secondImg, b_edit;
    Integer whichImage = 0;
    String id;
    private static final int camCode = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    ProductsHelper pHelper = new ProductsHelper();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        et_title = findViewById(R.id.et_title);
        et_price = findViewById(R.id.et_price);
        iv_image = findViewById(R.id.iv_image);
        iv_secondImage = findViewById(R.id.iv_secondImage);
        b_gallery = findViewById(R.id.b_gallery);
        b_camera = findViewById(R.id.b_camera);
        b_edit = findViewById(R.id.b_edit);
        b_firstImg = findViewById(R.id.b_firstImg);
        b_secondImg = findViewById(R.id.b_secondImg);




        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            et_title.setText(extras.getString("title"));
            et_price.setText(extras.getString("price"));
            iv_image.setImageBitmap(pHelper.stringToBitmap(extras.getString("image")));
        }


        b_firstImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVisibility();
                iv_image.setImageBitmap(pHelper.stringToBitmap(extras.getString("image")));
                whichImage = 1;
            }
        });

        b_secondImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                secImgVisibility();
                whichImage = 2;
                iv_image.setVisibility(View.INVISIBLE);
                iv_secondImage.setVisibility(View.VISIBLE);


            }
        });

        b_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pHelper.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE, getApplicationContext(), EditActivity.this)) {
                        pHelper.openGallery(iv_image, loadPhoto);
                }
            }
        });

        b_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pHelper.checkPermission(Manifest.permission.CAMERA, camCode, getApplicationContext(), EditActivity.this)) {
                        pHelper.openCamera(iv_image, takePhoto);
                }
            }
        });

        b_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditActivity.this);
                if (whichImage == 1) {
                    myDB.updateData(id, et_title.getText().toString(), et_price.getText().toString(), pHelper.bitmapToString(((BitmapDrawable)iv_image.getDrawable()).getBitmap()));
                } else if (whichImage == 2) {

                } else {
                    Toast.makeText(EditActivity.this, "Something went wrong :(", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void secImgVisibility() {
        if (iv_image.getVisibility() == View.VISIBLE) {
            iv_image.setVisibility(View.INVISIBLE);
        }
        if (iv_secondImage.getVisibility() == View.INVISIBLE) {
            iv_secondImage.setVisibility(View.VISIBLE);
        }
    }

    public void imgVisibility() {
        if (iv_image.getVisibility() == View.INVISIBLE) {
            iv_image.setVisibility(View.VISIBLE);
        }
        if (iv_secondImage.getVisibility() == View.VISIBLE) {
            iv_secondImage.setVisibility(View.INVISIBLE);
        }
    }






    // Gluposti ot AddActivity za kamerata
    ActivityResultLauncher<Intent> loadPhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Add same code that you want to add in onActivityResult method
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        try {
                            Uri selectedImage = result.getData().getData();
                            InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                            iv_image.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> takePhoto = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    // Add same code that you want to add in onActivityResult method
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bundle extras = result.getData().getExtras();
                        Bitmap image = (Bitmap) extras.get("data");
                        iv_image.setImageBitmap(image);
                    }
                }
            });


}