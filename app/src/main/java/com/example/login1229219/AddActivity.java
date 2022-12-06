package com.example.login1229219;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.login1229219.DataBases.MyDatabaseHelper;
import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.Helpers.ProductsHelper;

import java.io.IOException;
import java.io.InputStream;

public class AddActivity extends AppCompatActivity {
    EditText et_title, et_price;
    Button b_add, b_gallery, b_camera, b_imgOne, b_imgTwo;
    ImageView iv_image, iv_imageTwo;
    String user;
    int whichImage = 1;
//    int imgCheck = 0;
    ProductsHelper pHelper = new ProductsHelper();
    NavigationHelper nHelper = new NavigationHelper();

    private static final int camCode = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        et_title = findViewById(R.id.et_title);
        et_price = findViewById(R.id.et_price);
        b_add = findViewById(R.id.b_add);
        iv_image = findViewById(R.id.iv_image);
        b_gallery = findViewById(R.id.b_gallery);
        b_camera = findViewById(R.id.b_camera);
        b_imgOne = findViewById(R.id.b_imgOne);
        b_imgTwo = findViewById(R.id.b_imgTwo);
        iv_imageTwo = findViewById(R.id.iv_imageTwo);



        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("author");
        }

        b_imgOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVisibility();
                whichImage = 1;
            }
        });

        b_imgTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgTwoVisibility();
                whichImage = 2;
            }
        });

        b_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (pHelper.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE, getApplicationContext(), AddActivity.this)) {

                   if (whichImage == 1) {
                       pHelper.openGallery(iv_image, loadPhoto);
                   } else if (whichImage == 2) {
                       pHelper.openGallery(iv_imageTwo, loadPhoto);
                   }

               }
            }
        });

        b_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pHelper.checkPermission(Manifest.permission.CAMERA, camCode, getApplicationContext(), AddActivity.this)) {

                    if (whichImage == 1) {
                        pHelper.openCamera(iv_image, takePhoto);
                    } else if (whichImage == 2) {
                        pHelper.openCamera(iv_imageTwo, takePhoto);
                    }
                }
            }
        });

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageCheck(iv_image, iv_imageTwo)) {
                    MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                    myDB.addProduct(et_title.getText().toString().trim(), user, Integer.valueOf(et_price.getText().toString().trim()), pHelper.bitmapToString(((BitmapDrawable) iv_image.getDrawable()).getBitmap()), pHelper.bitmapToString(((BitmapDrawable) iv_imageTwo.getDrawable()).getBitmap()));
                    nHelper.goToUserProductsString(getApplicationContext(), user);
                } else {
                    Toast.makeText(AddActivity.this, "Please upload Main image and second image.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
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
                            if (whichImage == 1) {
                                iv_image.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                            } else if (whichImage == 2) {
                                iv_imageTwo.setImageBitmap(BitmapFactory.decodeStream(imageStream));
                            }

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
                        if (whichImage == 1) {
                            iv_image.setImageBitmap(image);
                        } else if (whichImage == 2) {
                            iv_imageTwo.setImageBitmap(image);
                        }
                    }

                }
            });




    public void imgVisibility() {
        if (iv_image.getVisibility() == View.INVISIBLE) {
            iv_image.setVisibility(View.VISIBLE);
        }
        if (iv_imageTwo.getVisibility() == View.VISIBLE) {
            iv_imageTwo.setVisibility(View.INVISIBLE);
        }
    }

    public void imgTwoVisibility() {
        if (iv_imageTwo.getVisibility() == View.INVISIBLE) {
            iv_imageTwo.setVisibility(View.VISIBLE);
        }
        if (iv_image.getVisibility() == View.VISIBLE) {
            iv_image.setVisibility(View.INVISIBLE);
        }
    }


    public boolean imageCheck(ImageView imageView, ImageView imageView2) {
        if (imageView.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fistimageone).getConstantState() || imageView.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.secimagetwo).getConstantState()) {
            return false;
        } else if (imageView2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.fistimageone).getConstantState() || imageView2.getDrawable().getConstantState() == getResources().getDrawable(R.drawable.secimagetwo).getConstantState())  {
            return false;
        }
        return true;
    }




}