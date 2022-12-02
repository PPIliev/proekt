package com.example.login1229219.ItemLists;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.login1229219.Adapters.CustomAdapter;
import com.example.login1229219.Dashboards.Ouser;
import com.example.login1229219.DataBases.MyDatabaseHelper;
import com.example.login1229219.EditActivity;
import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.ProductActivity;
import com.example.login1229219.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductsByUserList extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> product_id, product_title, product_author, product_price, product_image, product_imageTwo;
    CustomAdapter customAdapter;
    CustomAdapter.recyclerViewClickListener listener;
    CustomAdapter.recyclerViewMenuClick mListener;
    String user;
    Integer menuStuff;
    NavigationHelper nHelper = new NavigationHelper();

    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent(ProductsByUserList.this, Ouser.class);
        startActivity(i);

    }

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
        setContentView(R.layout.activity_products_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);
        menuStuff = 0;

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("author");
        }

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nHelper.goToAddActivity(getApplicationContext(), user);
            }
        });



        myDB = new MyDatabaseHelper(ProductsByUserList.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_author = new ArrayList<>();
        product_price = new ArrayList<>();
        product_image = new ArrayList<>();
        product_imageTwo = new ArrayList<>();


        storeDataInArrays();

        setOnClickListener();
        customAdapter = new CustomAdapter(ProductsByUserList.this, product_id, product_title, product_author, product_price, product_image, listener, mListener);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsByUserList.this));


    }



    //onclick

    private void setOnClickListener() {
        listener = new CustomAdapter.recyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {


//                    Intent i = new Intent(getApplicationContext(), ProductActivity.class);
//                    i.putExtra("title", product_title.get(position));
//                    i.putExtra("author", product_author.get(position));
//                    i.putExtra("price", product_price.get(position));
//                    i.putExtra("image", product_image.get(position));
//
//
//                    startActivity(i);

            }
        };

        mListener = new CustomAdapter.recyclerViewMenuClick() {
            @Override
            public void onMenuItem(int position, MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.m_view:
                        Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                        i.putExtra("id", product_id.get(position));
                        i.putExtra("title", product_title.get(position));
                        i.putExtra("author", product_author.get(position));
                        i.putExtra("price", product_price.get(position));
                        i.putExtra("image", product_image.get(position));
                        i.putExtra("imageTwo", product_imageTwo.get(position));




                        startActivity(i);
                        break;
                    case R.id.m_edit:
                        Intent intent = new Intent(getApplicationContext(), EditActivity.class);
                        intent.putExtra("id", product_id.get(position));
                        intent.putExtra("title", product_title.get(position));
//                        intent.putExtra("author", product_author.get(position));
                        intent.putExtra("price", product_price.get(position));
                        intent.putExtra("image", product_image.get(position));

                        startActivity(intent);
                        break;

                    case R.id.m_delete:
                        MyDatabaseHelper myDB = new MyDatabaseHelper(ProductsByUserList.this);
                        String productID = product_id.get(position);
                        String productTitle = product_title.get(position);
                        confirmDialog(myDB, productID, productTitle);

                }
                }
            };
        }





    public void storeDataInArrays() {
        Cursor cursor = myDB.readUserData(user);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                product_id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                product_author.add(cursor.getString(2));
                product_price.add(cursor.getString(3));
                product_image.add(cursor.getString(4));
                product_imageTwo.add(cursor.getString(5));
            }
        }
    }


    public void confirmDialog(MyDatabaseHelper myDB, String productID, String productTitle) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("DELETE" + productTitle +"?");
        builder.setMessage("Are you sure you want to DELETE this product ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                myDB.deleteOneProduct(productID);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }


}
