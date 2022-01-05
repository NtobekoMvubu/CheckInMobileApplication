package com.example.bookedit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper( Context context) {
        super(context, "BookedIt.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
    DB.execSQL("create Table userdetails(email TEXT primary key, firstname TEXT, surname TEXT,password TEXT, phone_number TEXT, gender TEXT, dob TEXT )");
    DB.execSQL("create Table reservations(firstname TEXT, email TEXT, hotel_name TEXT, checkindate TEXT, checkoutdate TEXT, noofpeople TEXT, price TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int ii) {
    DB.execSQL("drop Table if exists userdetails");
    DB.execSQL("drop Table if exists reservations");
    }

    //insert method for signing up a new user to the app
    public boolean insertuser(String email, String firstname, String surname, String password, String phone_number, String gender, String dob ) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("firstname", firstname);
        contentValues.put("surname", surname);
        contentValues.put("password", password);
        contentValues.put("phone_number", phone_number);
        contentValues.put("gender", gender);
        contentValues.put("dob", dob);
        long result = DB.insert("userdetails", null, contentValues);
        if (result == -1)
        {
            return false;
        } else
        {
            return true;
        }
    }

    public Cursor getData(String email, String password){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor pointer = DB.rawQuery("select * from userdetails where email = ? AND password = ? ",  new String[]{email, password});
        return pointer;
    }

    public Cursor getuserdetails (String email)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from userdetails where email = ?" , new String[]{email});
        return cursor;
    }

    public boolean insertRes(String name, String email, String hotelname, String checkindate, String checkoutdate, String noofpeople, String price)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("firstname", name);
        contentValues.put("email", email);
        contentValues.put("hotel_name", hotelname);
        contentValues.put("checkindate", checkindate);
        contentValues.put("checkoutdate", checkoutdate);
        contentValues.put("noofpeople", noofpeople);
        contentValues.put("price", price);
        long result = DB.insert("reservations", null, contentValues);
        if (result == -1)
        {
            return false;
        } else
        {
            return true;
        }

    }

    public Cursor getRes () {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from reservations", null);
        return cursor;
    }

    public Cursor getPRes (String checkindate, String checkoutdate, String hotel_name ){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("select * from reservations where checkindate = ? AND checkoutdate = ? AND hotel_name = ?  ", new String[]{checkindate, checkoutdate, hotel_name });
        return cursor;
    }

}
