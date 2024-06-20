package com.example.tfg.jesus;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;
import com.example.tfg.jc.MenuActivity;

public class PerfilActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        dbHelper = new DatabaseHelper(this); // Inicializa el helper de base de datos

        // Recupera el userId almacenado en SharedPreferences.
        SharedPreferences prefs = getSharedPreferences("tfg_preferences", MODE_PRIVATE);
        int userId = prefs.getInt("userId", 0);
        Log.d("PerfilActivity", "Recuperado userId de preferencias compartidas: " + userId);

        // Configura y muestra la información del usuario en la actividad
        TextView phoneTextView = findViewById(R.id.phoneTextView);
        String phone = getUserPhone(String.valueOf(userId));
        phoneTextView.setText(phone);

        TextView emailTextView = findViewById(R.id.emailTextView);
        String email = getUserEmail(String.valueOf(userId));
        emailTextView.setText(email);

        TextView bodyTypeTextView = findViewById(R.id.bodyTypeTextView);
        String bodyType = getUserBodyType(String.valueOf(userId));
        bodyTypeTextView.setText(bodyType);

        TextView nombreTextView = findViewById(R.id.nombreTextView);
        String nombre = getUserName(String.valueOf(userId));
        nombreTextView.setText(nombre);

        TextView idTextView = findViewById(R.id.idTextView);
        String userIdFromDb = getUserId(String.valueOf(userId));
        idTextView.setText("ID: " + userIdFromDb);

        TextView subscriptionTextView = findViewById(R.id.subscriptionTextView);
        String userSubscription = getUserSubscription(String.valueOf(userId));
        subscriptionTextView.setText(userSubscription);

        TextView objectiveTextView = findViewById(R.id.objectiveTextView);
        String userObjective = getUserObjective(String.valueOf(userId));
        objectiveTextView.setText(userObjective);

        TextView apellidoTextView = findViewById(R.id.apellidoTextView);
        String apellido = getUserLastName(String.valueOf(userId));
        apellidoTextView.setText(apellido);

        // Configura un clic en la flecha de regreso al menú principal
        ImageView imageView = findViewById(R.id.ic_arrow_image);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PerfilActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    // Métodos para obtener la información del usuario desde la base de datos
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
        if (!userObjective.isEmpty()) {
            userObjective = userObjective.substring(0, 1).toUpperCase() + userObjective.substring(1).toLowerCase();
        }
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