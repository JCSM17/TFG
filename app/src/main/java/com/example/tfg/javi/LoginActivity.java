package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.databinding.ActivityLoginBinding;
import com.example.tfg.jc.MenuActivity;

public class LoginActivity extends AppCompatActivity {

    // Constantes para el nombre de las preferencias y la clave para el estado de pago
    private static final String PREFERENCES_NAME = "tfg_preferences";
    private static final String PAGADO = "pagado";

    ActivityLoginBinding binding; // Referencia al binding de la actividad
    DatabaseHelper databaseHelper; // Instancia del helper de la base de datos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this); // Inicializa el helper de la base de datos

        binding.botonIniciarSesion.setOnClickListener(this::handleLogin); // Configura el listener para el botón de inicio de sesión
        binding.textoRegistro.setOnClickListener(view -> navigateToFragment(new RegistroFragment())); // Configura el listener para el texto de registro
    }

    // Navega a un fragmento específico
    private void navigateToFragment(Fragment fragment) {
        Intent intent = new Intent(this, FragmentContainerActivity.class);
        intent.putExtra("fragmentName", fragment.getClass().getName());
        Log.d("LoginActivity", "Fragment name: " + fragment.getClass().getName());
        startActivity(intent);
    }

    // Maneja la lógica de inicio de sesión
    private void handleLogin(View view) {
        String email = binding.entradaEmail.getText().toString();
        String password = binding.entradaContrasenia.getText().toString();

        Log.d("LoginActivity", "Email ingresado: " + email);
        Log.d("LoginActivity", "Contraseña ingresada: " + password);

        // Validación del correo electrónico ingresado
        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(password)) { // Validación de la contraseña ingresada
            showToast(getString(R.string.empty_password));
        } else {
            String storedPassword = databaseHelper.getPasswordByEmail(email); // Obtiene la contraseña almacenada

            Log.d("LoginActivity", "Contraseña almacenada: " + storedPassword);

            // Verifica si la contraseña ingresada coincide con la almacenada
            if (storedPassword != null && storedPassword.equals(databaseHelper.hashPassword(password))) {
                navigateToActivity(); // Navega a la actividad principal si las credenciales son correctas
            } else {
                String hashedPassword = databaseHelper.hashPassword(password); // Obtiene el hash de la contraseña ingresada

                if (hashedPassword == null) {
                    Log.e("LoginActivity", "Error al hashear la contraseña");
                    return;
                }
                Log.d("LoginActivity", "Contraseña hasheada: " + hashedPassword);

                // Verifica las credenciales utilizando el método alternativo
                Boolean checkCredentials = databaseHelper.checkEmailPassword(email, hashedPassword);
                Log.d("LoginActivity", "Resultado de checkEmailPassword: " + checkCredentials);

                if (checkCredentials) {
                    RegistroData registroData = databaseHelper.getRegistroByEmail(email);
                    Log.d("LoginActivity", "RegistroData obtenido: " + registroData);

                    if (registroData != null) {
                        navigateToActivity(); // Navega a la actividad principal si se encuentra el registro
                    } else {
                        showToast(getString(R.string.user_not_registered));
                        navigateToFragment(new RegistroFragment()); // Navega al fragmento de registro si no se encuentra el registro
                    }
                } else {
                    showToast(getString(R.string.invalid_credentials));
                    navigateToFragment(new RegistroFragment()); // Navega al fragmento de registro si las credenciales no son válidas
                }
            }
        }
    }

    // Navega a la actividad principal y finaliza la actividad actual
    private void navigateToActivity() {
        showToast(getString(R.string.login_success));
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    // Muestra un mensaje de toast con el mensaje dado
    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}