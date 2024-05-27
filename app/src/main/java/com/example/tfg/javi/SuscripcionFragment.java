package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class SuscripcionFragment extends Fragment {

    EditText nombreEditText, apellidoEditText, dniEditText, telefonoEditText, emailEditText;
    TextView descripcionTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_suscripcion, container, false);

        // Obtener referencias a los EditText
        nombreEditText = view.findViewById(R.id.nombre);
        apellidoEditText = view.findViewById(R.id.apellidos);
        dniEditText = view.findViewById(R.id.dni);
        telefonoEditText = view.findViewById(R.id.telefono);
        emailEditText = view.findViewById(R.id.email);

        // Obtener referencia a TextView
        descripcionTextView = view.findViewById(R.id.descripcion);

        // Obtener el plan seleccionado por el usuario
        String plan = getArguments().getString("plan", "anual"); // "anual" es el valor predeterminado

        // Actualizar el texto de la vista TextView para reflejar el plan seleccionado
        descripcionTextView.setText("SUSCRIPCIÓN DEL PLAN " + plan.toUpperCase());

        // Obtener referencia al botón "Suscribirme ahora"
        Button suscribirmeButton = view.findViewById(R.id.boton_suscripcion);

        // Configurar el OnClickListener para el botón
        suscribirmeButton.setOnClickListener(v -> {
            // Validar los campos de entrada antes de proceder con la suscripción
            if (validarCampos()) {
                // Obtener el email del usuario
                String email = emailEditText.getText().toString().trim();

                // Actualizar el tipo de suscripción del usuario
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                boolean updateSuccessful = databaseHelper.updateSubscriptionType(email, plan);
                if (!updateSuccessful) {
                    // Manejar el caso en que la actualización no fue exitosa
                    showToast(getString(R.string.update_failed_message));
                    return;
                }

                // Obtener el ID del usuario
                int userId = databaseHelper.getUserIdByEmail(email);
                if (userId == -1) {
                    // Manejar el caso en que no se encontró el usuario
                    showToast(getString(R.string.user_not_found_message));
                    return;
                }

                UserData userData = new UserData();
                userData.setSelectedId(userId);
                // Aquí debes establecer los demás campos de userData con los valores apropiados

                // Insertar los datos adicionales del usuario en la tabla TABLE_USERDATA
                long result = databaseHelper.insertUserData(userData);
                if (result == -1) {
                    // Manejar el caso en que la inserción no fue exitosa
                    showToast(getString(R.string.insert_user_data_failed_message));
                    return;
                }

                // Crear un intent para iniciar la actividad SuscripcionConfirmActivity
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
            showToast(getString(R.string.empty_field_message));
            return false;
        }

        // Validar el formato del DNI
        if (!dni.matches(getString(R.string.dni_pattern))) {
            showToast(getString(R.string.invalid_dni_message));
            return false;
        }

        // Validar el número de teléfono
        if (!telefono.matches(getString(R.string.telefono_pattern))) {
            showToast(getString(R.string.invalid_telefono_message));
            return false;
        }

        // Validar el formato del email
        if (!email.matches(getString(R.string.email_pattern))) {
            showToast(getString(R.string.invalid_email_message));
            return false;
        }
        return true;
    }

    private void showToast(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }
}