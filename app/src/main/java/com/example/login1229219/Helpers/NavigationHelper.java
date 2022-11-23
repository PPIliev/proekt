package com.example.login1229219.Helpers;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.login1229219.AddActivity;
import com.example.login1229219.Login;
import com.example.login1229219.MainActivity;
import com.example.login1229219.ItemLists.ProductsByUserList;
import com.example.login1229219.ItemLists.ProductsList;
import com.example.login1229219.Register;

public class NavigationHelper {

    public NavigationHelper() {
    }

    //AddActivity ->
    public void goToProductsList(Context context) {
        Intent i = new Intent(context, ProductsByUserList.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //Register ->
    public void goToLogin(Context context) {
        String reg = "You have successfully registered, please login to your account";
        Intent i = new Intent(context, Login.class);
        i.putExtra("registered", reg);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    //ProductsList ->
    public void goToAddActivity(Context context, String user) {
        Intent i = new Intent(context, AddActivity.class);
        i.putExtra("author", user);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //OUser Dashboard - >
    public void goToMain(Context context) {
        Intent i = new Intent(context, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
    public void goToProducts(Context context, TextView tv_user) {
        Intent i = new Intent(context, ProductsList.class);
        i.putExtra("author", tv_user.getText().toString());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void goToUserProducts(Context context, TextView tv_user) {
        Intent i = new Intent(context, ProductsByUserList.class);
        i.putExtra("author", tv_user.getText().toString());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //MainActivity ->
    public void goLogin(Context context) {
        Intent i = new Intent(context, Login.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void goRegister(Context context) {
        Intent i = new Intent(context, Register.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }




}
