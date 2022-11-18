package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ProductsByUserList extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;

    MyDatabaseHelper myDB;
    ArrayList<String> product_id, product_title, product_author, product_price, product_image;
    CustomAdapter customAdapter;
    CustomAdapter.recyclerViewClickListener listener;
    CustomAdapter.recyclerViewMenuClick mListener;
    String user;
    Integer menuStuff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

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
                Intent i = new Intent(ProductsByUserList.this, AddActivity.class);
              i.putExtra("author", user);
                startActivity(i);
            }
        });



        myDB = new MyDatabaseHelper(ProductsByUserList.this);
        product_id = new ArrayList<>();
        product_title = new ArrayList<>();
        product_author = new ArrayList<>();
        product_price = new ArrayList<>();
        product_image = new ArrayList<>();

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
            }
        }
    }


}
