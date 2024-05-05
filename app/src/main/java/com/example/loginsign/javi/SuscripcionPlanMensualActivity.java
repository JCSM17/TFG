package com.example.loginsign.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsign.R;

public class SuscripcionPlanMensualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripcionplanmensual);

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = findViewById(R.id.button4);

        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                Intent intent = new Intent(SuscripcionPlanMensualActivity.this, SuscripcionMensualAnualConfirmActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });
    }
}
