package com.example.loginsign;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsign.SuscripcionPlanMensualActivity;

public class PlanMensualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planmensual);

        // Obtener referencia al botón "VERPELPLANANUAL"
        Button verPlanAnualButton = findViewById(R.id.buttonVerPlanAnual);

        // Configurar el OnClickListener para el botón
        verPlanAnualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad PlanAnualActivity
                Intent intent = new Intent(PlanMensualActivity.this, PlanAnualActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        // Obtener referencia al botón "Elegir plan mensual"
        Button elegirPlanMensualButton = findViewById(R.id.button3);

        // Configurar el OnClickListener para el botón
        elegirPlanMensualButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad SuscripcionPlanMensualActivity
                Intent intent = new Intent(PlanMensualActivity.this, SuscripcionPlanMensualActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });
    }
}
