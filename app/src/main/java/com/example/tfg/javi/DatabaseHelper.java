package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JJJFit.db";
    public static final String TABLE_REGISTRO = "registro";
    public static final String TABLE_INICIOSESION = "iniciosesion";
    public static final String TABLE_SUSCRIPANUAL = "suscripanual";
    public static final String TABLE_SUSCRIPMENSUAL = "suscripmensual";
    public static final String TABLE_USERDATA = "userData";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_NOMBRE = "nombre";
    public static final String COLUMN_APELLIDO = "apellido";
    public static final String COLUMN_TELEFONO = "telefono";
    public static final String COLUMN_ESTATURA = "estatura";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_BODYTYPE = "bodyType";

    private static final String SELECT_FROM_WHERE = "SELECT * FROM %s WHERE %s = ?";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        Log.d("DatabaseHelper", "Creating tables...");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_INICIOSESION + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " + COLUMN_PASSWORD + " TEXT)");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_REGISTRO + "(" + COLUMN_EMAIL + " TEXT primary key," +
                COLUMN_PASSWORD + " TEXT," +
                COLUMN_NOMBRE + " TEXT, " +
                COLUMN_APELLIDO + " TEXT," +
                COLUMN_TELEFONO + " TEXT)");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_SUSCRIPANUAL + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_NOMBRE + " TEXT," +
                COLUMN_APELLIDO + " TEXT," +
                "dni TEXT," +
                COLUMN_TELEFONO + " TEXT)");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_SUSCRIPMENSUAL + "(" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                "nombre1 TEXT," +
                "apellido1 TEXT," +
                "dni1 TEXT," +
                "telefono1 TEXT)");

        // Agrega una nueva tabla para los datos del usuario
        MyDatabase.execSQL("CREATE TABLE " + TABLE_USERDATA + "(" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                COLUMN_ESTATURA + " TEXT," +
                COLUMN_EDAD + " TEXT," +
                COLUMN_GENERO + " TEXT," +
                COLUMN_BODYTYPE + " TEXT)");

        Log.d("DatabaseHelper", "Tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_INICIOSESION);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPANUAL);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPMENSUAL);
        // Asegúrate de que también eliminas esta tabla en onUpgrade
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);

        onCreate(MyDB);
    }

    private boolean insertData(String table, ContentValues contentValues) {
        SQLiteDatabase MyDatabase = null;
        try {
            MyDatabase = this.getWritableDatabase();
            MyDatabase.beginTransaction();
            SQLiteStatement stmt = MyDatabase.compileStatement("INSERT INTO " + table + " VALUES(?, ?, ?, ?, ?)");
            stmt.bindString(1, contentValues.getAsString(COLUMN_EMAIL));
            stmt.bindString(2, contentValues.getAsString(COLUMN_PASSWORD));
            stmt.bindString(3, contentValues.getAsString(COLUMN_NOMBRE));
            stmt.bindString(4, contentValues.getAsString(COLUMN_APELLIDO));
            stmt.bindString(5, contentValues.getAsString(COLUMN_TELEFONO));
            long result = stmt.executeInsert();
            if (result == -1) {
                return false;
            }
            MyDatabase.setTransactionSuccessful();
            return true;
        } finally {
            if (MyDatabase != null) {
                MyDatabase.endTransaction();
                MyDatabase.close();
            }
        }
    }

    public boolean insertData(String email, String password, String nombre, String apellido, String telefono) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, hashPassword(password));
        contentValues.put(COLUMN_NOMBRE, nombre);
        contentValues.put(COLUMN_APELLIDO, apellido);
        contentValues.put(COLUMN_TELEFONO, telefono);
        return insertData(TABLE_REGISTRO, contentValues);
    }

    public boolean insertData(String email, String password) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, hashPassword(password));
        return insertData(TABLE_INICIOSESION, contentValues);
    }

    public boolean insertDataSuscripcionAnual(String email, String nombre, String apellido, String dni, String telefono, String tarjeta, String fechaExpiracion, String cvc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_NOMBRE, nombre);
        contentValues.put(COLUMN_APELLIDO, apellido);
        contentValues.put("dni", dni);
        contentValues.put(COLUMN_TELEFONO, telefono);
        contentValues.put("creditcard", tarjeta);
        contentValues.put("expirationdate", fechaExpiracion);
        contentValues.put("cvc", cvc);
        return insertData(TABLE_SUSCRIPANUAL, contentValues);
    }

    public boolean insertUserData(String email, String estatura, String edad, String genero) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ESTATURA, estatura);
        contentValues.put(COLUMN_EDAD, edad);
        contentValues.put(COLUMN_GENERO, genero);
        return insertData(TABLE_USERDATA, contentValues);
    }

    public boolean updateUserBodyType(String email, String bodyType) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BODYTYPE, bodyType);
        int result = MyDatabase.update(TABLE_USERDATA, contentValues, COLUMN_EMAIL + " = ?", new String[]{email});
        MyDatabase.close();
        return result > 0;
    }

    public boolean checkEmail(String email) {
        SQLiteDatabase MyDatabase = this.getReadableDatabase();
        Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL), new String[]{email});
        boolean result = cursor.getCount() > 0;
        cursor.close();
        MyDatabase.close();
        return result;
    }


    public boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase MyDatabase = null;
        Cursor cursor = null;
        try {
            MyDatabase = this.getReadableDatabase();
            cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD), new String[]{email, hashPassword(password)});
            boolean result = cursor.getCount() > 0;
            return result;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (MyDatabase != null) {
                MyDatabase.close();
            }
        }
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            Log.e("DatabaseHelper", "Error al calcular el hash de la contraseña", e);
            return null;
        }
    }
}