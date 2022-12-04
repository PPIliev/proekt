package com.example.login1229219.ItemLists;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.login1229219.Adapters.CustomAdapter2;
import com.example.login1229219.DataBases.MyDatabaseHelper;
import com.example.login1229219.Helpers.NavigationHelper;
import com.example.login1229219.ProductActivity;
import com.example.login1229219.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductsList extends AppCompatActivity {
    RecyclerView recyclerView;
//    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> product_id, product_title, product_author, product_price, product_image;
    CustomAdapter2 customAdapter;
    CustomAdapter2.recyclerViewClickListener listener;
    String user;
    NavigationHelper nHelper = new NavigationHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        recyclerView = findViewById(R.id.recyclerView2);
//        add_button = findViewById(R.id.add_button);

//        customAdapter.notifyDataSetChanged();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            user = extras.getString("author");
        }

//        add_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                nHelper.goToAddActivity(getApplicationContext(), user);
//            }
//        });



        myDB = new MyDatabaseHelper(ProductsList.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_author = new ArrayList<>();
        product_price = new ArrayList<>();
        product_image = new ArrayList<>();

        storeDataInArrays();

        setOnClickListener();
        customAdapter = new CustomAdapter2(ProductsList.this, product_id, product_title, product_author, product_price, product_image, listener);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsList.this));


    }
    //onclick
    private void setOnClickListener() {
        listener = new CustomAdapter2.recyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(getApplicationContext(), ProductActivity.class);
                i.putExtra("title", product_title.get(position));
                i.putExtra("author", product_author.get(position));
                i.putExtra("price", product_price.get(position));
                i.putExtra("image", product_image.get(position));



                startActivity(i);
            }
        };
    }

    public void storeDataInArrays() {
        Cursor cursor = myDB.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                product_id.add(cursor.getString(0));
                product_title.add(cursor.getString(1));
                product_author.add(cursor.getString(2));
                product_price.add(cursor.getString(3));
                product_image.add(cursor.getString(4));
            }
        }
    }
}
