package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class PlanMensualActivity extends AppCompatActivity {

    private static final Class PLAN_ANUAL_ACTIVITY = PlanAnualActivity.class;
    private static final Class SUSCRIPCION_PLAN_MENSUAL_ACTIVITY = SuscripcionPlanMensualActivity.class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_mensual);

        // Obtener referencia al botón "Ver el plan anual"
        Button verPlanAnualButton = findViewById(R.id.buttonVerPlanAnual);

        // Configurar el OnClickListener para el botón "Ver el plan anual"
        verPlanAnualButton.setOnClickListener(v -> startNewActivity(PLAN_ANUAL_ACTIVITY));

        // Obtener referencia al botón "Elegir plan mensual"
        Button elegirPlanMensualButton = findViewById(R.id.button3);

        // Configurar el OnClickListener para el botón "Elegir plan mensual"
        elegirPlanMensualButton.setOnClickListener(v -> startNewActivity(SUSCRIPCION_PLAN_MENSUAL_ACTIVITY));
    }

    private void startNewActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}