package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class SuscripcionPlanMensualFragment extends Fragment {

    private static final String DNI_PATTERN = "\\d{8}[a-zA-Z]";
    private static final String TELEFONO_PATTERN = "\\d{9}";
    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._%+-]+@(gmail|outlook|yahoo)\\.(com|es|net|org|edu|gov|info|biz|co\\.uk)";
    private static final String EMPTY_FIELD_MESSAGE = "Por favor, complete todos los campos";
    private static final String INVALID_DNI_MESSAGE = "Por favor, introduce un DNI válido";
    private static final String INVALID_TELEFONO_MESSAGE = "Introduce un número de teléfono válido";
    private static final String INVALID_EMAIL_MESSAGE = "Por favor, introduce un correo válido (@gmail, @outlook, @yahoo)";

    EditText nombreEditText, apellidoEditText, dniEditText, telefonoEditText, emailEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suscripcion_mensual, container, false);

        // Obtener referencias a los EditText
        nombreEditText = view.findViewById(R.id.susplmens_name);
        apellidoEditText = view.findViewById(R.id.susplmens_lastname);
        dniEditText = view.findViewById(R.id.susplmens_dni);
        telefonoEditText = view.findViewById(R.id.susplmens_phonenumber);
        emailEditText = view.findViewById(R.id.susplmens_email);

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = view.findViewById(R.id.button4);

        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(v -> {
            // Validar los campos de entrada antes de proceder con la suscripción
            if (validarCampos()) {
                // Crear un intent para iniciar la actividad SuscripcionMensualAnualConfirmActivity
                Intent intent = new Intent(getActivity(), PasarelaActivity.class);
                startActivity(intent); // Iniciar la actividad
            }
        });

        return view;
    }

    private boolean validarCampos() {
        String nombre = nombreEditText.getText().toString().trim();
        String apellido = apellidoEditText.getText().toString().trim();
        String dni = dniEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();

        if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || telefono.isEmpty() || email.isEmpty()) {
            showToast(EMPTY_FIELD_MESSAGE);
            return false;
        }

        // Validar el formato del DNI
        if (!dni.matches(DNI_PATTERN)) {
            showToast(INVALID_DNI_MESSAGE);
            return false;
        }

        // Validar el número de teléfono
        if (!telefono.matches(TELEFONO_PATTERN)) {
            showToast(INVALID_TELEFONO_MESSAGE);
            return false;
        }

        // Validar el formato del email
        if (!email.matches(EMAIL_PATTERN)) {
            showToast(INVALID_EMAIL_MESSAGE);
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}