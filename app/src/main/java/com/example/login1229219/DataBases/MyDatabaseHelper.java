package com.example.login1229219.DataBases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "Products.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "products";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "product_title";
    private static final String COLUMN_AUTHOR = "product_author";
    private static final String COLUMN_PRICE = "product_price";
    private static final String COLUMN_IMAGE = "product_image";
    private static final String COLUMN_IMAGE2 = "product_image2";

    //Constructor
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_AUTHOR + " TEXT, " +
                COLUMN_PRICE + " INTEGER, " +
                COLUMN_IMAGE + " TEXT, " +
                COLUMN_IMAGE2 + " TEXT);";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addProduct(String title, String author, int price, String image, String imageTwo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_AUTHOR, author);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_IMAGE, image);
        cv.put(COLUMN_IMAGE2, imageTwo);
        long result = db.insert(TABLE_NAME, null, cv);
        if(result == -1) {
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public Cursor readUserData(String user) {
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_AUTHOR + " = " + "'" + user + "'";
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }


    public void updateData(String row_id, String title, String price, String image) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TITLE, title);
        cv.put(COLUMN_PRICE, price);
        cv.put(COLUMN_IMAGE, image);

        long result = db.update(TABLE_NAME, cv, "_id=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully updated", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteOneProduct (String row_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[] {row_id});
        if (result == -1) {
            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully deleted", Toast.LENGTH_SHORT).show();
        }

    }

}
