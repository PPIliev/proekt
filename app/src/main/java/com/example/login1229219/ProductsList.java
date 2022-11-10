package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductsList extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> product_id, product_title, product_author, product_price;
    CustomAdapter customAdapter;
    CustomAdapter.recyclerViewClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        recyclerView = findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.add_button);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProductsList.this, AddActivity.class);
                startActivity(i);
            }
        });

        myDB = new MyDatabaseHelper(ProductsList.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_author = new ArrayList<>();
        product_price = new ArrayList<>();

        storeDataInArrays();

        setOnClickListener();
        customAdapter = new CustomAdapter(ProductsList.this, product_id, product_title, product_author, product_price, listener);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductsList.this));


    }
    //onclick
    private void setOnClickListener() {
        listener = new CustomAdapter.recyclerViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                Intent i = new Intent(getApplicationContext(),ProductActivity.class);
                i.putExtra("title", product_title.get(position));
                i.putExtra("author", product_author.get(position));
                i.putExtra("price", product_price.get(position));


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
            }
        }
    }
}
