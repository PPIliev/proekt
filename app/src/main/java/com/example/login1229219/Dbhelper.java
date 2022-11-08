package com.example.login1229219;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {

    private static final String USERS_TABLE = "USERS_TABLE";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_USERS_USERNAMES = "USERS_USERNAMES";
    public static final String COLUMN_USERS_PASSWORD = "USERS_PASSWORDS";
    public static final String COLUMN_USERS_EMAIL = "USERS_EMAIL";
    public static final String  COLUMN_USERS_TYPE = "USERS_TYPE";

    public Dbhelper(@Nullable Context context) {
        super(context, "users.db", null,1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USERS_TABLE +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USERS_USERNAMES +
                " TEXT, " + COLUMN_USERS_PASSWORD + " VARCHAR, " + COLUMN_USERS_EMAIL + " TEXT, " + COLUMN_USERS_TYPE + " INTEGER)";
        db.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //Insert user in DB
    public boolean insertUser(UsersModel usersModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_USERS_USERNAMES, usersModel.getUsername());
        cv.put(COLUMN_USERS_EMAIL, usersModel.getEmail());
        cv.put(COLUMN_USERS_PASSWORD, usersModel.getPassword());
        cv.put(COLUMN_USERS_TYPE, usersModel.getType());

        long insert = db.insert(USERS_TABLE, null, cv);
        if (insert == -1) {
            return false;
        }
        return true;

    }
    //Check if user is "Normal" or "Other" type
    public int checkUserType(String username, String password) {
        SQLiteDatabase db = getReadableDatabase();
        int type = 0;

        String searchQuery = "SELECT * FROM " +
                USERS_TABLE + " WHERE " + COLUMN_USERS_USERNAMES + " = " + "'" + username +
                "'" + " AND " +  COLUMN_USERS_PASSWORD + " = " + "'" + password + "'";
        Cursor cursor = db.rawQuery(searchQuery ,null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            type = cursor.getInt(cursor.getColumnIndexOrThrow("USERS_TYPE"));
        }


        cursor.close();
        db.close();
        return type;
    }

    // The username has already been taken
    public boolean checkSameUsername(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String searchQuery = "SELECT * FROM " +
                USERS_TABLE + " WHERE " + COLUMN_USERS_USERNAMES + " = " + "'" + username +
                "'";
        Cursor cursor = db.rawQuery(searchQuery, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }

    //There is an existing account with this email address
    public boolean checkSameEmail(String email) {
        SQLiteDatabase db = getReadableDatabase();
        String searchQuery = "SELECT * FROM " +
                USERS_TABLE + " WHERE " + COLUMN_USERS_EMAIL + " = " + "'" + email +
                "'";
        Cursor cursor = db.rawQuery(searchQuery, null);
        if (cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return false;
        }
        cursor.close();
        db.close();
        return true;
    }


//    public boolean checkUsers(String username, String password) {
//        SQLiteDatabase db = getReadableDatabase();
//        String searchQuery = "SELECT * FROM " +
//                USERS_TABLE + " WHERE " + COLUMN_USERS_USERNAMES + " = " + "'" + username +
//                "'" + " AND " +  COLUMN_USERS_PASSWORD + " = " + "'" + password + "'";
//        Cursor cursor = db.rawQuery(searchQuery ,null);
//        if (cursor.getCount() > 0) {
//            return true;
//        }
//        cursor.close();
//        db.close();
//        return false;
//    }

}
