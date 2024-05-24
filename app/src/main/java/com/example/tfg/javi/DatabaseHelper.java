package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
    public static final String COLUMN_OBJETIVO = "objetivo";
    public static final String COLUMN_ESTATURA = "estatura";
    public static final String COLUMN_EDAD = "edad";
    public static final String COLUMN_GENERO = "genero";
    public static final String COLUMN_PESO = "peso"; // Added this line
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
                COLUMN_PESO + " TEXT," +
                COLUMN_BODYTYPE + " TEXT," +
                COLUMN_OBJETIVO + " TEXT)"); // Añade esta línea

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

    public boolean insertData(String table, ContentValues contentValues) {
        try (SQLiteDatabase MyDatabase = this.getWritableDatabase()) {
            MyDatabase.beginTransaction();
            long result = MyDatabase.insert(table, null, contentValues);
            if (result == -1) {
                return false;
            }
            MyDatabase.setTransactionSuccessful();
            return true;
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

    public boolean insertUserData(String email, float estatura, int edad, String genero, float peso, String objetivo) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_ESTATURA, estatura);
        contentValues.put(COLUMN_EDAD, edad);
        contentValues.put(COLUMN_GENERO, genero);
        contentValues.put(COLUMN_PESO, peso);
        contentValues.put(COLUMN_OBJETIVO, objetivo); // Añade esta línea
        return insertData(TABLE_USERDATA, contentValues);
    }

    public String getUserGoal(String email) {
        String selectQuery = "SELECT " + COLUMN_OBJETIVO + " FROM " + TABLE_USERDATA + " WHERE " + COLUMN_EMAIL + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{email});
        String objetivo = "";
        if (cursor.moveToFirst()) {
            objetivo = cursor.getString(0);
        }
        cursor.close();
        return objetivo;
    }

    public boolean updateUserBodyType(String email, String bodyType) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BODYTYPE, bodyType);
        int result = MyDatabase.update(TABLE_USERDATA, contentValues, COLUMN_EMAIL + " = ?", new String[]{email});
        MyDatabase.close();
        return result > 0;
    }

    public String getUserGoal() {
        // Asume que tienes una tabla de usuarios y una columna de objetivo
        String selectQuery = "SELECT objetivo FROM usuarios LIMIT 1"; // Cambia la consulta según tu esquema de base de datos
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String objetivo = "";
        if (cursor.moveToFirst()) {
            objetivo = cursor.getString(0);
        }
        cursor.close();
        return objetivo;
    }


    public boolean checkEmail(String email) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase();
             Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL), new String[]{email})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkEmailPassword(String email, String password) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase();
             Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD), new String[]{email, hashPassword(password)})) {
            return cursor.getCount() > 0;
        }
    }

    public String hashPassword(String password) {
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