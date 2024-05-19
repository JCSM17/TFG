package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

public class SuscripcionPlanMensualActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripcion_plan_mensual);

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = findViewById(R.id.button4);

        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validar los campos de entrada antes de proceder con la suscripción
                if (validarCampos()) {
                    // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                    Intent intent = new Intent(SuscripcionPlanMensualActivity.this, PasarelaActivity.class);
                    startActivity(intent); // Iniciar la actividad
                }
            }
        });
    }

    private boolean validarCampos() {
        // Obtener referencias a los EditText
        EditText nombreEditText = findViewById(R.id.susplmens_name);
        EditText apellidoEditText = findViewById(R.id.susplmens_lastname);
        EditText dniEditText = findViewById(R.id.susplmens_dni);
        EditText telefonoEditText = findViewById(R.id.susplmens_phonenumber);
        EditText emailEditText = findViewById(R.id.susplmens_email);

        // Obtener los valores de los campos de texto
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String dni = dniEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        // Verificar si algún campo está vacío
        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato del DNI (8 números seguidos de una letra)
        if (!dni.matches("\\d{8}[a-zA-Z]")) {
            Toast.makeText(this, "Por favor, introduce un DNI válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el número de teléfono (9 números)
        if (!telefono.matches("\\d{9}")) {
            Toast.makeText(this, "Introduce un número de teléfono válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato del email (@gmail, @outlook, @yahoo)
        if (!email.matches("[a-zA-Z0-9._%+-]+@(gmail|outlook|yahoo)\\.(com|es|net|org|edu|gov|info|biz|co\\.uk)")) {
            Toast.makeText(this, "Por favor, introduce un correo válido (@gmail, @outlook, @yahoo)", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
