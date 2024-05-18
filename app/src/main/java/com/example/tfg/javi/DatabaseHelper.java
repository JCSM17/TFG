package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "LogSign.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        MyDatabase.execSQL("CREATE TABLE iniciosesion(email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("create Table registro(email TEXT primary key," +
                "password TEXT," +
                "nombre TEXT, " +
                "apellido TEXT," +
                "telefono TEXT)");
        MyDatabase.execSQL("CREATE TABLE suscripanual(email TEXT PRIMARY KEY, " +
                "nombre TEXT," +
                "apellido TEXT," +
                "dni TEXT," +
                "telefono TEXT," +
                "creditcard NUMBER," +
                "expirationdate DATE," +
                "cvc NUMBER)");
        MyDatabase.execSQL("CREATE TABLE suscripmensual(email TEXT PRIMARY KEY, " +
                "nombre1 TEXT," +
                "apellido1 TEXT," +
                "dni1 TEXT," +
                "telefono1 TEXT," +
                "creditcard1 NUMBER," +
                "expirationdate1 DATE," +
                "cvc1 NUMBER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("drop Table if exists registro");
        MyDB.execSQL("drop Table if exists iniciosesion");
        MyDB.execSQL("drop Table if exists suscripanual");
    }

    public Boolean insertData(String email, String password, String nombre, String apellido, String telefono) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        contentValues.put("telefono", telefono);
        long result = MyDatabase.insert("registro", null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean insertData(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("password", password);
        long result = MyDatabase.insert("iniciosesion", null, contentValues);
        return result != -1;
    }

    public boolean insertDataSuscripcionAnual(String email, String nombre, String apellido, String dni, String telefono, String tarjeta, String fechaExpiracion, String cvc) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("email", email);
        contentValues.put("nombre", nombre);
        contentValues.put("apellido", apellido);
        contentValues.put("dni", dni);
        contentValues.put("telefono", telefono);
        contentValues.put("creditcard", tarjeta);
        contentValues.put("expirationdate", fechaExpiracion);
        contentValues.put("cvc", cvc);
        long result = MyDatabase.insert("suscripanual", null, contentValues);
        return result != -1;
    }


    public Boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from registro where email = ?", new String[]{email});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from registro where email = ? and password = ?", new String[]{email, password});

        if (cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}