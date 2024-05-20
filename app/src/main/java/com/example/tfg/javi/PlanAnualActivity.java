package com.example.tfg.javi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tfg.R;

public class PlanAnualActivity extends AppCompatActivity {

    private static final Class PLAN_MENSUAL_ACTIVITY = PlanMensualActivity.class;
    private static final Class SUSCRIPCION_PLAN_ANUAL_ACTIVITY = SuscripcionPlanAnualActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_anual);

        // Obtener referencia al botón "Ver el plan mensual"
        Button verPlanMensualButton = findViewById(R.id.botonVerPlanMensual);

        // Configurar el OnClickListener para el botón "Ver el plan mensual"
        verPlanMensualButton.setOnClickListener(v -> startNewActivity(PLAN_MENSUAL_ACTIVITY));

        // Obtener referencia al botón "Elegir plan anual"
        Button elegirPlanAnualButton = findViewById(R.id.botonElegirPlanAnual);

        // Configurar el OnClickListener para el botón "Elegir plan anual"
        elegirPlanAnualButton.setOnClickListener(v -> startNewActivity(SUSCRIPCION_PLAN_ANUAL_ACTIVITY));
    }

    private void startNewActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}