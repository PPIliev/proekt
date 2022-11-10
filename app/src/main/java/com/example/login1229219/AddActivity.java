package com.example.login1229219;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText et_author, et_title, et_price;
    Button b_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        et_title = findViewById(R.id.et_title);
        et_author = findViewById(R.id.et_author);
        et_price = findViewById(R.id.et_price);
        b_add = findViewById(R.id.b_add);

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(AddActivity.this);
                myDB.addProduct(et_title.getText().toString().trim(), et_author.getText().toString().trim(), Integer.valueOf(et_price.getText().toString().trim()));
                goToList();
            }
        });

    }

    public void goToList() {
        Intent i = new Intent(AddActivity.this, ProductsList.class);
        startActivity(i);
    }

}