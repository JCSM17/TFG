package com.example.tfg.jesus;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

public class PerfilActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        dbHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("tfg_preferences", MODE_PRIVATE);
        int userId = prefs.getInt("userId", 0);
        Log.d("PerfilActivity", "Recuperado userId de preferencias compartidas: " + userId);

        TextView phoneTextView = findViewById(R.id.phoneTextView); // Asegúrate de que este ID coincide con el ID de tu TextView en el archivo XML
        String phone = getUserPhone(String.valueOf(userId));
        phoneTextView.setText(phone);

        TextView emailTextView = findViewById(R.id.emailTextView); // Reemplaza 'emailTextView' con el ID real de tu TextView para el correo electrónico
        String email = getUserEmail(String.valueOf(userId));
        emailTextView.setText(email);

        TextView bodyTypeTextView = findViewById(R.id.bodyTypeTextView); // Reemplaza 'bodyTypeTextView' con el ID real de tu TextView para el tipo de cuerpo
        String bodyType = getUserBodyType(String.valueOf(userId));
        bodyTypeTextView.setText(bodyType);

        TextView nombreTextView = findViewById(R.id.nombreTextView); // Reemplaza 'nombreTextView' con el ID real de tu TextView para el nombre
        String nombre = getUserName(String.valueOf(userId));
        nombreTextView.setText(nombre);

        TextView idTextView = findViewById(R.id.idTextView); // Reemplaza 'idTextView' con el ID real de tu TextView para el ID
        String userIdFromDb = getUserId(String.valueOf(userId));
        idTextView.setText("ID: " + userIdFromDb);

        TextView subscriptionTextView = findViewById(R.id.subscriptionTextView); // Reemplaza 'subscriptionTextView' con el ID real de tu TextView para la suscripción
        String userSubscription = getUserSubscription(String.valueOf(userId));
        subscriptionTextView.setText(userSubscription);

        TextView objectiveTextView = findViewById(R.id.objectiveTextView); // Reemplaza 'objectiveTextView' con el ID real de tu TextView para el objetivo
        String userObjective = getUserObjective(String.valueOf(userId));
        objectiveTextView.setText(userObjective);

        TextView apellidoTextView = findViewById(R.id.apellidoTextView); // Reemplaza 'apellidoTextView' con el ID real de tu TextView para el apellido
        String apellido = getUserLastName(String.valueOf(userId));
        apellidoTextView.setText(apellido);




    }

    private String getUserPhone(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_TELEFONO + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String phone = "";
        if (cursor.moveToFirst()) {
            phone = cursor.getString(0);
        }
        cursor.close();
        return phone;
    }

    private String getUserEmail(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_EMAIL + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String email = "";
        if (cursor.moveToFirst()) {
            email = cursor.getString(0);
        }
        cursor.close();
        return email;
    }
    private String getUserBodyType(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_BODYTYPE + " FROM " + DatabaseHelper.TABLE_USERDATA + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String bodyType = "";
        if (cursor.moveToFirst()) {
            bodyType = cursor.getString(0);
        }
        cursor.close();
        return bodyType;
    }

    private String getUserName(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_NOMBRE + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String nombre = "";
        if (cursor.moveToFirst()) {
            nombre = cursor.getString(0);
        }
        cursor.close();
        return nombre;
    }

    private String getUserId(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_ID + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String userId = "";
        if (cursor.moveToFirst()) {
            userId = cursor.getString(0);
        }
        cursor.close();
        return userId;
    }

    private String getUserSubscription(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_SUSCRIPCION + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String userSubscription = "";
        if (cursor.moveToFirst()) {
            userSubscription = cursor.getString(0);
        }
        cursor.close();
        if (!userSubscription.isEmpty()) {
            userSubscription = userSubscription.substring(0, 1).toUpperCase() + userSubscription.substring(1);
        }
        return userSubscription;
    }



    private String getUserObjective(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_OBJETIVO + " FROM " + DatabaseHelper.TABLE_USERDATA + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String userObjective = "";
        if (cursor.moveToFirst()) {
            userObjective = cursor.getString(0);
        }
        cursor.close();
        return userObjective;
    }
    private String getUserLastName(String id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT " + DatabaseHelper.COLUMN_APELLIDO + " FROM " + DatabaseHelper.TABLE_REGISTRO + " WHERE " + DatabaseHelper.COLUMN_ID + " = ?", new String[]{id});
        String apellido = "";
        if (cursor.moveToFirst()) {
            apellido = cursor.getString(0);
        }
        cursor.close();
        return apellido;
    }

}