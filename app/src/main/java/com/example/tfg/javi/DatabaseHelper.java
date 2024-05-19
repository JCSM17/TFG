package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String databaseName = "LogSign.db";

    public DatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        Log.d("DatabaseHelper", "Creating tables...");
        MyDatabase.execSQL("CREATE TABLE iniciosesion(email TEXT PRIMARY KEY, password TEXT)");
        MyDatabase.execSQL("create Table registro(email TEXT primary key," +
                "password TEXT," +
                "nombre TEXT, " +
                "apellido TEXT," +
                "telefono TEXT)");
        MyDatabase.execSQL("CREATE TABLE suscripanual(email TEXT PRIMARY KEY, " +
                "nombre TEXT,"+
                "apellido TEXT,"+
                "dni TEXT,"+
                "telefono TEXT)");
        MyDatabase.execSQL("CREATE TABLE suscripmensual(email TEXT PRIMARY KEY, " +
                "nombre1 TEXT,"+
                "apellido1 TEXT,"+
                "dni1 TEXT,"+
                "telefono1 TEXT)");

        Log.d("DatabaseHelper", "Tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        MyDB.execSQL("drop Table if exists registro");
        MyDB.execSQL("drop Table if exists iniciosesion");
        MyDB.execSQL("drop Table if exists suscripanual");
        MyDB.execSQL("drop Table if exists suscripmensual"); // Asegúrate de que también eliminas esta tabla en onUpgrade
        onCreate(MyDB);
    }


    public Boolean insertData(String email, String password, String nombre, String apellido, String telefono){
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

    public Boolean insertData(String email, String password){
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
    // Reemplaza tus funciones existentes con las mejoradas aquí
    public Boolean checkEmail(String email){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from registro where email = ?", new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close(); // Cierra el cursor para evitar fugas de memoria
        return result;
    }

    public Boolean checkEmailPassword(String email, String password){
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery("Select * from registro where email = ? and password = ?", new String[]{email, password});
        boolean result = cursor.getCount() > 0;
        cursor.close(); // Cierra el cursor para evitar fugas de memoria
        return result;
    }
}