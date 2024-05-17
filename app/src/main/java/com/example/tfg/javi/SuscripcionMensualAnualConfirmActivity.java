package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.example.tfg.R;

public class SuscripcionMensualAnualConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripcion_mensual_anual_confirm);

        // Aquí puedes realizar cualquier inicialización adicional que necesites

        // Por ejemplo, si deseas mostrar un mensaje de confirmación
        TextView confirmationMessageTextView = findViewById(R.id.confirmationMessageTextView);
        confirmationMessageTextView.setText("¡Gracias por suscribirte!");

        // Obtener referencia al botón "Ir a la pantalla principal"
        Button goToMainButton = findViewById(R.id.button5);

        // Configurar el OnClickListener para el botón
        goToMainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear un intent para abrir PrincipalActivity
                Intent intent = new Intent(SuscripcionMensualAnualConfirmActivity.this, PrincipalActivity.class);
                startActivity(intent); // Iniciar PrincipalActivity
            }
        });
    }
}
