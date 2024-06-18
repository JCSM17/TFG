package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.security.MessageDigest;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "JJJFit.db";
    public static final String TABLE_REGISTRO = "registro";
    public static final String COLUMN_ID = "id";
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
    public static final String COLUMN_SEXO = "sexo";
    public static final String COLUMN_PESO = "peso";
    public static final String COLUMN_BODYTYPE = "bodyType";
    public static final String COLUMN_CALORIAS = "calorias"; // Nuevo campo para las calorías
    private static final String SELECT_FROM_WHERE = "SELECT * FROM %s WHERE %s = ?";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDatabase) {
        Log.d("DatabaseHelper", "Creating tables...");
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
                COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_ESTATURA + " REAL," +
                COLUMN_EDAD + " INTEGER," +
                COLUMN_SEXO + " TEXT," +
                COLUMN_PESO + " REAL," +
                COLUMN_OBJETIVO + " TEXT," +
                COLUMN_BODYTYPE + " TEXT," +
                COLUMN_CALORIAS + " REAL," + // Agrega el campo de calorías
                "FOREIGN KEY(" + COLUMN_ID + ") REFERENCES " + TABLE_REGISTRO + "(" + COLUMN_ID + "))"); // Agrega la restricción de clave externa

        Log.d("DatabaseHelper", "Tables created.");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int oldVersion, int newVersion) {
        Log.d("DatabaseHelper", "Upgrading database from version " + oldVersion + " to " + newVersion);
        if (oldVersion < 3) {
            MyDB.execSQL("ALTER TABLE " + TABLE_USERDATA + " ADD COLUMN " + COLUMN_EMAIL + " TEXT");
        }
        if (oldVersion < newVersion) {
            MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO);
            MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPANUAL);
            MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_SUSCRIPMENSUAL);
            MyDB.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDATA);
            onCreate(MyDB);
        }
    }

    public boolean updateSubscriptionType(long userId, String subscriptionType) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SUSCRIPCION, subscriptionType);
        int result = db.update(TABLE_REGISTRO, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(userId)});
        db.close();
        return result > 0;
    }

    public RegistroData getRegistroByColumn(String columnName, String columnValue) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_REGISTRO, null, columnName + "=?", new String[]{columnValue}, null, null, null);
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

    // Usa el nuevo método para obtener un usuario por correo electrónico
    public RegistroData getRegistroByEmail(String email) {
        return getRegistroByColumn(COLUMN_EMAIL, email);
    }

    // Usa el nuevo método para obtener un usuario por ID
    public RegistroData getRegistroPorId(long userId) {
        return getRegistroByColumn(COLUMN_ID, String.valueOf(userId));
    }

    public long insertUserData(UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_ID, userData.getId());
        contentValues.put(COLUMN_ESTATURA, userData.getEstatura());
        contentValues.put(COLUMN_EDAD, userData.getEdad());
        contentValues.put(COLUMN_SEXO, userData.getSexo());
        contentValues.put(COLUMN_PESO, userData.getPeso());
        contentValues.put(COLUMN_OBJETIVO, userData.getObjetivo());
        contentValues.put(COLUMN_CALORIAS, userData.getCalorias());

        long result = db.insertWithOnConflict(TABLE_USERDATA, null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);
        if (result == -1) {
            Log.e("DatabaseHelper", "Error al insertar los datos del usuario: " + userData);
        } else {
            Log.d("DatabaseHelper", "Datos del usuario insertados correctamente en la fila ID: " + result);
        }

        db.close();
        return result;
    }

    public long insertData(String table, ContentValues contentValues) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.insert(table, null, contentValues);
        db.close();
        return result;
    }

    public UserData getUserData(int userId) {
        Log.d("DatabaseHelper", "getUserData() called with userId: " + userId);
        try (SQLiteDatabase db = this.getReadableDatabase(); Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ID + " = ?", new String[]{String.valueOf(userId)})) {
            UserData userData = null;
            if (cursor.moveToFirst()) {
                int estaturaIndex = cursor.getColumnIndex(COLUMN_ESTATURA);
                int edadIndex = cursor.getColumnIndex(COLUMN_EDAD);
                int generoIndex = cursor.getColumnIndex(COLUMN_SEXO);
                int pesoIndex = cursor.getColumnIndex(COLUMN_PESO);
                int objetivoIndex = cursor.getColumnIndex(COLUMN_OBJETIVO);
                int caloriasIndex = cursor.getColumnIndex(COLUMN_CALORIAS); // Obtener el índice de la columna de las calorías

                if (estaturaIndex != -1 && edadIndex != -1 && generoIndex != -1 && pesoIndex != -1 && objetivoIndex != -1 && caloriasIndex != -1) {
                    float estatura = cursor.getFloat(estaturaIndex);
                    int edad = cursor.getInt(edadIndex);
                    String genero = cursor.getString(generoIndex);
                    float peso = cursor.getFloat(pesoIndex);
                    String objetivo = cursor.getString(objetivoIndex);
                    float calorias = cursor.getFloat(caloriasIndex); // Obtener las calorías de la base de datos
                    int idIndex = cursor.getColumnIndex(COLUMN_ID);
                    if (idIndex != -1) {
                        int id = cursor.getInt(idIndex);
                        userData = new UserData(id, estatura, edad, genero, peso, objetivo, calorias);
                        Log.d("DatabaseHelper", "getUserData() returned: " + userData);
                    }
                }
            } else {
                Log.e("DatabaseHelper", "getUserData(): No user data found for userId: " + userId);
            }
            return userData;
        } catch (Exception e) {
            Log.e("DatabaseHelper", "getUserData(): Error retrieving user data for userId: " + userId, e);
            return null;
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

    public String getUserGoal(int userId) {
        String selectQuery = "SELECT " + COLUMN_OBJETIVO + " FROM " + TABLE_USERDATA + " WHERE " + COLUMN_ID + " = ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userId)});
        String objetivo = "";
        if (cursor.moveToFirst()) {
            objetivo = cursor.getString(0);
        }
        cursor.close();
        return objetivo;
    }

    public boolean updateUserBodyType(int userId, String bodyType) {
        SQLiteDatabase MyDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BODYTYPE, bodyType);
        int result = MyDatabase.update(TABLE_USERDATA, contentValues, COLUMN_ID + " = ?", new String[]{String.valueOf(userId)});
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

    public int getUserId(String... email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor;
        if (email.length > 0 && email[0] != null && !email[0].isEmpty()) {
            cursor = db.query(TABLE_REGISTRO, new String[]{COLUMN_ID}, COLUMN_EMAIL + "=?", email, null, null, null);
        } else {
            cursor = db.query(TABLE_USERDATA, new String[]{COLUMN_ID}, null, null, null, null, null);
        }
        int userId = -1; // Returns -1 if no user is found
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return userId;
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

    public String hashPassword(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (Exception e) {
            Log.e("DatabaseHelper", "Error al hacer hash de la contraseña", e);
            return null;
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