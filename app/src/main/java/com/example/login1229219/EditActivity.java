package com.example.login1229219;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditActivity extends AppCompatActivity {
    EditText et_title, et_price;
    ImageView iv_image, iv_secondImage;
    Button b_gallery, b_camera, b_firstImg, b_secondImg, b_edit;
    Integer whichImage = 0;
    String id;


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
            iv_image.setImageBitmap(stringToBitmap(extras.getString("image")));
        }


        b_firstImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgVisibility();
                iv_image.setImageBitmap(stringToBitmap(extras.getString("image")));
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
                openGallery(iv_image);
//                image = ((BitmapDrawable)iv_image.getDrawable()).getBitmap();
            }
        });

        b_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera(iv_image);
//                image = ((BitmapDrawable)iv_image.getDrawable()).getBitmap();
            }
        });

        b_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(EditActivity.this);
                if (whichImage == 1) {
                    myDB.updateData(id, et_title.getText().toString(), et_price.getText().toString(), bitmapToString(((BitmapDrawable)iv_image.getDrawable()).getBitmap()));
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


    private Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
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
    public void openGallery(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestStoragePermission();
        }
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        loadPhoto.launch(i);
    }

    public void openCamera(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestCameraPermission();
        }
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (i.resolveActivity(getPackageManager()) != null) {

            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
        }
        takePhoto.launch(i);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }
}