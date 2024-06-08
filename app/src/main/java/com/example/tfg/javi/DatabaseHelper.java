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
    public static final String COLUMN_ID = "id";
    public static final String TABLE_INICIOSESION = "iniciosesion";

    public static final String COLUMN_SUSCRIPCION = "tipo_suscripcion";
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
    public static final String COLUMN_SEXO = "genero";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_BODYTYPE = "bodyType";
    public static final String COLUMN_CALORIAS = "calorias"; // Nuevo campo para las calorías

    private static final String SELECT_FROM_WHERE = "SELECT * FROM %s WHERE %s = ?";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        Log.d("DatabaseHelper", "Creating tables...");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_INICIOSESION + "(" +
                COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT NOT NULL)");
        MyDatabase.execSQL("CREATE TABLE " + TABLE_REGISTRO + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_EMAIL + " TEXT UNIQUE NOT NULL," +
                COLUMN_PASSWORD + " TEXT NOT NULL," +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_APELLIDO + " TEXT NOT NULL," +
                COLUMN_TELEFONO + " INTEGER NOT NULL," +
                COLUMN_SUSCRIPCION + " TEXT," +
                "subscription_start_date INTEGER," +
                "subscription_duration INTEGER)");

        // Agrega una nueva tabla para los datos del usuario
        MyDatabase.execSQL("CREATE TABLE " + TABLE_USERDATA + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY," + // Use COLUMN_ID as the primary key
                "selectedId INTEGER," +
                COLUMN_ESTATURA + " TEXT," +
                COLUMN_EDAD + " TEXT," +
                COLUMN_SEXO + " TEXT," +
                COLUMN_PESO + " TEXT," +
                COLUMN_BODYTYPE + " TEXT," +
                COLUMN_OBJETIVO + " TEXT," +
                COLUMN_CALORIAS + " REAL)"); // Agrega el campo de calorías

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

    public boolean updateSubscriptionType(long userId, String subscriptionType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SUSCRIPCION, subscriptionType);
        int result = db.update(TABLE_REGISTRO, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
        return result > 0;
    }

    public RegistroData getRegistroByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRO, null, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        if (cursor.moveToFirst()) {
            RegistroData registroData = new RegistroData();
            int columnIndex;

            columnIndex = cursor.getColumnIndex(COLUMN_ID);
            if (columnIndex != -1) {
                registroData.setId(cursor.getInt(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            if (columnIndex != -1) {
                registroData.setEmail(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex != -1) {
                registroData.setPassword(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_NOMBRE);
            if (columnIndex != -1) {
                registroData.setNombre(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_APELLIDO);
            if (columnIndex != -1) {
                registroData.setApellido(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_TELEFONO);
            if (columnIndex != -1) {
                registroData.setTelefono(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_SUSCRIPCION);
            if (columnIndex != -1) {
                registroData.setSuscripcion(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex("subscription_start_date");
            if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
                registroData.setFechaInicioSuscripcion(cursor.getLong(columnIndex));
            }

            columnIndex = cursor.getColumnIndex("subscription_duration");
            if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
                registroData.setDuracionSuscripcion(cursor.getInt(columnIndex));
            }

            cursor.close();
            return registroData;
        }
        cursor.close();
        return null;
    }

    public RegistroData getRegistroPorId(long userId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRO, null, COLUMN_ID + "=?", new String[]{String.valueOf(userId)}, null, null, null);
        if (cursor.moveToFirst()) {
            RegistroData registroData = new RegistroData();
            int columnIndex;

            columnIndex = cursor.getColumnIndex(COLUMN_ID);
            if (columnIndex != -1) {
                registroData.setId(cursor.getInt(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_EMAIL);
            if (columnIndex != -1) {
                registroData.setEmail(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_PASSWORD);
            if (columnIndex != -1) {
                registroData.setPassword(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_NOMBRE);
            if (columnIndex != -1) {
                registroData.setNombre(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_APELLIDO);
            if (columnIndex != -1) {
                registroData.setApellido(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_TELEFONO);
            if (columnIndex != -1) {
                registroData.setTelefono(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex(COLUMN_SUSCRIPCION);
            if (columnIndex != -1) {
                registroData.setSuscripcion(cursor.getString(columnIndex));
            }

            columnIndex = cursor.getColumnIndex("subscription_start_date");
            if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
                registroData.setFechaInicioSuscripcion(cursor.getLong(columnIndex));
            }

            columnIndex = cursor.getColumnIndex("subscription_duration");
            if (columnIndex != -1 && !cursor.isNull(columnIndex)) {
                registroData.setDuracionSuscripcion(cursor.getInt(columnIndex));
            }

            cursor.close();
            return registroData;
        }
        cursor.close();
        return null;
    }

    public long insertUserData(UserData userData) {
        try (SQLiteDatabase db = this.getWritableDatabase()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_EMAIL, userData.getEmail());
            contentValues.put("selectedId", userData.getSelectedId());
            contentValues.put(COLUMN_ESTATURA, userData.getEstatura());
            contentValues.put(COLUMN_EDAD, userData.getEdad());
            contentValues.put(COLUMN_SEXO, userData.getSexo());
            contentValues.put(COLUMN_PESO, userData.getPeso());
            contentValues.put(COLUMN_OBJETIVO, userData.getObjetivo());
            contentValues.put(COLUMN_CALORIAS, userData.getCalorias());

            return db.insert(TABLE_USERDATA, null, contentValues);
        }
    }

    public long insertData(String table, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, contentValues);
        db.close();
        return result;
    }

    public UserData getUserData(int userId) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getReadableDatabase();
            cursor = db.rawQuery("SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
            UserData userData = null;
            if (cursor.moveToFirst()) {
                int emailIndex = cursor.getColumnIndex(COLUMN_EMAIL);
                int selectedIdIndex = cursor.getColumnIndex("selectedId");
                int estaturaIndex = cursor.getColumnIndex(COLUMN_ESTATURA);
                int edadIndex = cursor.getColumnIndex(COLUMN_EDAD);
                int generoIndex = cursor.getColumnIndex(COLUMN_SEXO);
                int pesoIndex = cursor.getColumnIndex(COLUMN_PESO);
                int objetivoIndex = cursor.getColumnIndex(COLUMN_OBJETIVO);

                if (emailIndex != -1 && selectedIdIndex != -1 && estaturaIndex != -1 && edadIndex != -1 && generoIndex != -1 && pesoIndex != -1 && objetivoIndex != -1) {
                    String email = cursor.getString(emailIndex);
                    int selectedId = cursor.getInt(selectedIdIndex);
                    float estatura = cursor.getFloat(estaturaIndex);
                    int edad = cursor.getInt(edadIndex);
                    String genero = cursor.getString(generoIndex);
                    float peso = cursor.getFloat(pesoIndex);
                    String objetivo = cursor.getString(objetivoIndex);
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    if (idIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        userData = new UserData(id, email, selectedId, estatura, edad, genero, peso, objetivo, userData.getCalorias());
                    }
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

    public boolean checkUserEmail(String email) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase();
             Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL), new String[]{email})) {
            return cursor.getCount() > 0;
        }
    }

    public boolean checkUserId(int userId) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase();
             Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_ID), new String[]{String.valueOf(userId)})) {
            return cursor.getCount() > 0;
        }
    }

    public int getUserId(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRO, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        int userId = -1; // Devuelve -1 si no se encuentra el usuario
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return userId;
    }

    public int getUserIdByEmail(String email) {
        try (SQLiteDatabase db = this.getReadableDatabase();
             Cursor cursor = db.query(TABLE_REGISTRO, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null)) {
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(COLUMN_ID);
                if (columnIndex != -1) {
                    return cursor.getInt(columnIndex);
                }
            }
        }
        return -1; // Devuelve -1 si no se encontró el usuario o la columna
    }

    public boolean checkEmailPassword(String email, String password) {
        try (SQLiteDatabase MyDatabase = this.getReadableDatabase()) {
            String hashedPassword;
            try {
                hashedPassword = hashPassword(password);
            } catch (Exception e) {
                // Registra el error y devuelve false
                Log.e("DatabaseHelper", "Error al hacer hash de la contraseña", e);
                return false;
            }
            Cursor cursor = MyDatabase.rawQuery(String.format(SELECT_FROM_WHERE, TABLE_REGISTRO, COLUMN_EMAIL + " = ? AND " + COLUMN_PASSWORD + " = ?"), new String[]{email, hashedPassword});
            return cursor.getCount() > 0;
        }
    }

    public boolean isSubscriptionValid(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRO, new String[]{"subscription_start_date", "subscription_duration"}, COLUMN_EMAIL + "=?", new String[]{email}, null, null, null);
        boolean isValid = false;
        if (cursor.moveToFirst()) {
            long subscriptionStartDate = cursor.getLong(0);
            int subscriptionDuration = cursor.getInt(1);
            long currentDate = System.currentTimeMillis();
            long subscriptionEndDate = subscriptionStartDate + subscriptionDuration * 24 * 60 * 60 * 1000; // Convertir la duración de la suscripción de días a milisegundos
            isValid = currentDate < subscriptionEndDate;
        }
        cursor.close();
        db.close();
        return isValid;
    }

    public String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            // Registra el error y devuelve una cadena vacía
            Log.e("DatabaseHelper", "Error al hacer hash de la contraseña", e);
            return "";
        }
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