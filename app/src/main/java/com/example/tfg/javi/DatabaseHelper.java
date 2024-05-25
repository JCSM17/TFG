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
    public static final String COLUMN_PESO = "peso";
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
                "selectedId INTEGER," +
                COLUMN_ESTATURA + " TEXT," +
                COLUMN_EDAD + " TEXT," +
                COLUMN_GENERO + " TEXT," +
                COLUMN_PESO + " TEXT," +
                COLUMN_BODYTYPE + " TEXT," +
                COLUMN_OBJETIVO + " TEXT)");

        Log.d("DatabaseHelper", "Tables created.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_INICIOSESION);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPANUAL);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPMENSUAL);
        MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
        onCreate(MyDB);
    }

    public boolean insertUserData(UserData userData) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, userData.getEmail());
        contentValues.put("selectedId", userData.getSelectedId());
        contentValues.put(COLUMN_ESTATURA, userData.getEstatura());
        contentValues.put(COLUMN_EDAD, userData.getEdad());
        contentValues.put(COLUMN_GENERO, userData.getGenero());
        contentValues.put(COLUMN_PESO, userData.getPeso());
        contentValues.put(COLUMN_OBJETIVO, userData.getObjetivo());
        return insertData(TABLE_USERDATA, contentValues);
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

    public UserData getUserData(String email) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});
            UserData userData = null;
            if (cursor.moveToFirst()) {
                int selectedIdIndex = cursor.getColumnIndex("selectedId");
                int estaturaIndex = cursor.getColumnIndex(COLUMN_ESTATURA);
                int edadIndex = cursor.getColumnIndex(COLUMN_EDAD);
                int generoIndex = cursor.getColumnIndex(COLUMN_GENERO);
                int pesoIndex = cursor.getColumnIndex(COLUMN_PESO);
                int objetivoIndex = cursor.getColumnIndex(COLUMN_OBJETIVO);

                if (selectedIdIndex != -1 && estaturaIndex != -1 && edadIndex != -1 && generoIndex != -1 && pesoIndex != -1 && objetivoIndex != -1) {
                    int selectedId = cursor.getInt(selectedIdIndex);
                    float estatura = cursor.getFloat(estaturaIndex);
                    int edad = cursor.getInt(edadIndex);
                    String genero = cursor.getString(generoIndex);
                    float peso = cursor.getFloat(pesoIndex);
                    String objetivo = cursor.getString(objetivoIndex);
                    userData = new UserData(email, selectedId, estatura, edad, genero, peso, objetivo);
                }
            }
            return userData;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
    }

    public String getUserEmail() {
        String selectQuery = "SELECT " + COLUMN_EMAIL + " FROM " + TABLE_USERDATA;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        String email = "";
        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
        }
        cursor.close();
        return email;
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

    public boolean checkEmail(String email) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase();
             Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL), new String[]{email})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkEmailPassword(String email, String password) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase()) {
            String hashedPassword;
            try {
                hashedPassword = hashPassword(password);
            } catch (NoSuchAlgorithmException e) {
                // Registra el error y devuelve false
                Log.e("DatabaseHelper", "Error al hacer hash de la contraseña", e);
                return false;
            }
            Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD), new String[]{email, hashedPassword});
            return cursor.getCount() > 0;
        }
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}