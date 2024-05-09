package com.example.tfg.javi;

import static android.os.Build.VERSION_CODES.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SuscripcionPlanAnualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripcionplananual);

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = findViewById(R.id.button4);

        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                Intent intent = new Intent(SuscripcionPlanAnualActivity.this, SuscripcionMensualAnualConfirmActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });
    }
}
