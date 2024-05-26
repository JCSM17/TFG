package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;
import com.example.tfg.javi.Helper.TinyDB;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        TinyDB tinyDB = new TinyDB(this);
        String myObject = (String) tinyDB.getObject("myKey", String.class);

        // Ahora puedes usar myObject en tu actividad
    }

    // Método para el clic del botón (definido en el XML)
    public void irAPrincipal(View view) {
        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }
}