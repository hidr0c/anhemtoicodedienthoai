package com.sinhvien.anhemtoicodedienthoai.control;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.sinhvien.anhemtoicodedienthoai.model.Accounts;

import java.util.ArrayList;

public class AccountControl extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="MangaAccount";
    public static final int DATABASE_VERSION=1;
    public static final String DATABASE_PATH ="/data/data/database/account.db";
    public static final String TABLE_NAME="ACCOUNTS";
    public static final String IDAccount="id";
    public static final String Username="username";
    public static final String Password="password";
    public static final  String Email="email";


    public AccountControl(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    public boolean checkTaiKhoan(String username, String password){
        ArrayList<Accounts> lsAccount = loadData();
        for (Accounts a : lsAccount) {
            if (username.equals(a.getUsername()) && password.equals(a.getPassword()))
                return true;
            else
                continue;
        }
        return false;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db =SQLiteDatabase.openDatabase(DATABASE_PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        String sql= "Create table if not EXISTS " + TABLE_NAME + "("+ IDAccount + "INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL UNIQUE,"+Username+ "TEXT NOT NULL," + Password + "TEXT NOT NULL)";
        db.execSQL(sql);
        db.close();
    }
    public void insertData(String username, String password, String email){
        SQLiteDatabase db= SQLiteDatabase.openDatabase(DATABASE_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        ContentValues value= new ContentValues();
        value.put(Username,username);
        value.put(Password,password);
        value.put(Email,email);
        db.insert(TABLE_NAME,null,value);
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public ArrayList<Accounts> loadData(){
        ArrayList<Accounts> result= new ArrayList<>();
        SQLiteDatabase db= SQLiteDatabase.openDatabase(DATABASE_PATH,null,SQLiteDatabase.CREATE_IF_NECESSARY);
        Cursor cursor= db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        cursor.moveToFirst();
        do {
            Accounts account= new Accounts();
            account.setId(cursor.getInt(0));
            account.setUsername(cursor.getString(1));
            account.setPassword(cursor.getString(2));
            account.setEmail(cursor.getString(3));
            result.add(account);
        } while (cursor.moveToNext());
        return result;
    }
}
