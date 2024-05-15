package com.example.tfg.jc;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class SeleccionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion);

        // Definir los botones para cada opción
        Button btnVolumen = findViewById(R.id.btnVolumen);
        Button btnDefinicion = findViewById(R.id.btnDefinicion);
        Button btnAbdominales = findViewById(R.id.btnAbdominales);
        Button btnCardio = findViewById(R.id.btnCardio);

        // Establecer OnClickListener para cada botón
        btnVolumen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("volumen");
            }
        });

        btnDefinicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("definicion");
            }
        });

        btnAbdominales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("abdominales");
            }
        });

        btnCardio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirRutinasActivity("cardio");
            }
        });
    }

    // Método para abrir RutinasActivity con la opción seleccionada como un extra
    private void abrirRutinasActivity(String opcionSeleccionada) {
        Intent intent = new Intent(this, RutinasActivity.class);
        intent.putExtra("categoria_seleccionada", opcionSeleccionada);
        startActivity(intent);
    }
}
