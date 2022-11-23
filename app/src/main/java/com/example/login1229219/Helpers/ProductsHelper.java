package com.example.login1229219.Helpers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class ProductsHelper {
    private int id;
    private String title;
    private int price;
    private String image;
    private String secondImage;

    public ProductsHelper(int id, String title, int price, String image, String secondImage) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.image = image;
        this.secondImage = secondImage;
    }

    public ProductsHelper() {
    }

    public Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] encodeByte = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }


    public boolean checkPermission(String permission, int requestCode, Context context, Activity activity)
    {
        if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(activity, new String[] {permission}, requestCode);
            return false;
        }
        else {
//            Toast.makeText(context, "Permission granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public void openGallery(View view, ActivityResultLauncher<Intent> loadPhoto) {

        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        loadPhoto.launch(i);
    }

    public void openCamera(View view, ActivityResultLauncher<Intent> takePhoto) {
        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhoto.launch(i);
    }





    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSecondImage() {
        return secondImage;
    }

    public void setSecondImage(String secondImage) {
        this.secondImage = secondImage;
    }
}

