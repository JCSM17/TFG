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
}