package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                // Validar los campos de entrada antes de proceder con la suscripción
                if (validarCampos()) {
                    // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                    Intent intent = new Intent(SuscripcionPlanMensualActivity.this, SuscripcionMensualAnualConfirmActivity.class);
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
        EditText tarjetaEditText = findViewById(R.id.susplmens_creditcard);
        EditText cvcEditText = findViewById(R.id.susplmens_cvc);
        EditText fechaExpiracionEditText = findViewById(R.id.susplmens_expirationdate);

        // Obtener los valores de los campos de texto
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String dni = dniEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String tarjeta = tarjetaEditText.getText().toString().trim();
        String cvc = cvcEditText.getText().toString().trim();
        String fechaExpiracion = fechaExpiracionEditText.getText().toString().trim();

        // Verificar si algún campo está vacío
        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty() || tarjeta.isEmpty() || cvc.isEmpty() || fechaExpiracion.isEmpty()) {
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

        // Validar el número de tarjeta (1234-1234-1234-1234-1234-1234)
        if (!tarjeta.matches("\\d{4}-\\d{4}-\\d{4}-\\d{4}-\\d{4}-\\d{4}")) {
            Toast.makeText(this, "Por favor, introduce un número de tarjeta válido", Toast.LENGTH_SHORT).show();
            return false;
        }


        // Validar la fecha de expiración
        if (!isValidExpirationDate(fechaExpiracion)) {
            Toast.makeText(this, "La fecha de expiración debe ser posterior a la fecha actual", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Validar el formato del CVC (3 números)
        if (!cvc.matches("\\d{3}")) {
            Toast.makeText(this, "Por favor, introduce un CVC válido", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    // Método para validar el formato de la fecha (dd/MM/yyyy)
    private boolean isValidExpirationDate(String fechaExpiracion) {
        String regex = "^\\d{2}/\\d{2}/\\d{4}$";
        if (!fechaExpiracion.matches(regex)) {
            return false;
        }

        // Obtener la fecha actual
        Date fechaActual = new Date();

        // Convertir la fecha de expiración al formato Date
        Date fechaExpiracionDate = null;
        try {
            fechaExpiracionDate = new SimpleDateFormat("dd/MM/yyyy").parse(fechaExpiracion);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }

        // Verificar si la fecha de expiración es posterior a la fecha actual y posterior a 2024
        return !fechaExpiracionDate.before(fechaActual) && getYear(fechaExpiracionDate) >= 2024;
    }

    // Método para obtener el año de una fecha
    private int getYear(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        return Integer.parseInt(sdf.format(date));
    }
}
