package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class SuscripcionPlanAnualActivity extends AppCompatActivity {

    EditText nombreEditText, apellidoEditText, dniEditText, telefonoEditText, emailEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripcion_plan_anual);

        // Obtener referencias a los EditText
        nombreEditText = findViewById(R.id.susplanual_name);
        apellidoEditText = findViewById(R.id.susplanual_lastname);
        dniEditText = findViewById(R.id.susplanual_dni);
        telefonoEditText = findViewById(R.id.susplanual_phonenumber);
        emailEditText = findViewById(R.id.susplanual_email);

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = findViewById(R.id.button4);
        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar los campos de entrada antes de proceder con la suscripción
                if (validarCampos()) {
                    // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                    Intent intent = new Intent(SuscripcionPlanAnualActivity.this, PasarelaActivity.class);
                    startActivity(intent); // Iniciar la actividad
                }
            }
        });
    }

    private boolean validarCampos() {
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String dni = dniEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato del DNI
        if (!dni.matches("\\d{8}[a-zA-Z]")) {
            Toast.makeText(this, "Por favor, introduce un DNI válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el número de teléfono
        if (!telefono.matches("\\d{1,9}")) {
            Toast.makeText(this, "Introduce un número de teléfono válido.", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato del email
        if (!email.matches("[a-zA-Z0-9._%+-]+@(gmail|outlook|yahoo)\\.(com|es|net|org|edu|gov|info|biz|co\\.uk)")) {
            Toast.makeText(this, "Por favor, introduce un correo válido (@gmail, @outlook, @yahoo)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}