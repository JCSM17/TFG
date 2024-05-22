package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class TiendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiendaa);
    }

    // Método para el clic del botón (definido en el XML)
    public void irAPrincipal(View view) {
        Intent intent = new Intent(this, PlanAnualActivity.class);
        startActivity(intent);
    }
}
