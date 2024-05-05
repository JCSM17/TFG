package com.example.loginsign.javi;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.loginsign.R;

public class PlanAnualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plananual);

        // Obtener referencia al botón "Ver el plan mensual"
        Button verPlanMensualButton = findViewById(R.id.button2);

        // Configurar el OnClickListener para el botón "Ver el plan mensual"
        verPlanMensualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad PlanMensualActivity
                Intent intent = new Intent(PlanAnualActivity.this, PlanMensualActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        // Obtener referencia al botón "Elegir plan anual"
        Button elegirPlanAnualButton = findViewById(R.id.button3);

        // Configurar el OnClickListener para el botón "Elegir plan anual"
        elegirPlanAnualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad SuscripcionPlanAnualActivity
                Intent intent = new Intent(PlanAnualActivity.this, SuscripcionPlanAnualActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });
    }
}
