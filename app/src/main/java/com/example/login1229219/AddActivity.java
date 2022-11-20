package com.example.login1229219;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
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

public class AddActivity extends AppCompatActivity {
    EditText et_title, et_price;
    Button b_add, b_gallery, b_camera, b_imgOne, b_imgTwo;
    ImageView iv_image, iv_imageTwo;
    String user;
    int whichImage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

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
                if (whichImage == 1) {
                    openGallery(iv_image);
                } else if (whichImage == 2) {
                    openGallery(iv_imageTwo);
                }
            }
        });

        b_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (whichImage == 1) {
                    openCamera(iv_image);
                } else if (whichImage == 2) {
                    openCamera(iv_imageTwo);
                }

            }
        });

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addProduct(et_title.getText().toString().trim(), user, Integer.valueOf(et_price.getText().toString().trim()), bitmapToString(((BitmapDrawable)iv_image.getDrawable()).getBitmap()), bitmapToString(((BitmapDrawable)iv_imageTwo.getDrawable()).getBitmap()));
                goToList();
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
//Sloji permission-ite posle
    public void openGallery(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestStoragePermission();
        }
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        loadPhoto.launch(i);
//        startActivityForResult(Intent.createChooser(i, "Select Picture"), GALLERY_REQUEST);
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

    public void goToList() {
        Intent i = new Intent(AddActivity.this, ProductsList.class);
        startActivity(i);
    }

    private String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    private boolean checkCameraPermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean res2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED;
        return res1 && res2;
    }

    private boolean checkStoragePermission() {
        boolean res1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        return res1;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(new String[] {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

    }

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

}