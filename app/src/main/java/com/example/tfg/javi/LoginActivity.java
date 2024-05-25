package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.databinding.ActivityLoginBinding;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS = "PREFS";
    private static final String PAGADO = "pagado";
    private static final String INVALID_EMAIL = "Por favor, introduce una dirección de correo electrónico válida";
    private static final String EMPTY_PASSWORD = "Por favor, introduzca su contraseña";
    private static final String LOGIN_ERROR = "Error al insertar datos de inicio de sesión";
    private static final String INVALID_CREDENTIALS = "Credenciales no válidas";
    private static final String LOGIN_SUCCESS = "¡Has iniciado sesión exitosamente!";

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.botonIniciarSesion.setOnClickListener(this::handleLogin);
        binding.textoRegistro.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegistroActivity.class)));
    }

    private void handleLogin(View view) {
        String email = binding.entradaEmail.getText().toString();
        String password = binding.entradaContrasenia.getText().toString();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(LoginActivity.this, INVALID_EMAIL, Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(LoginActivity.this, EMPTY_PASSWORD, Toast.LENGTH_SHORT).show();
        } else {
            Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

            if (checkCredentials) {
                ContentValues contentValues = new ContentValues();
                contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
                try {
                    contentValues.put(DatabaseHelper.COLUMN_PASSWORD, databaseHelper.hashPassword(password));
                } catch (NoSuchAlgorithmException e) {
                    // Manejar la excepción aquí, por ejemplo, imprimir la traza de la pila:
                    e.printStackTrace();
                }
                try {
                    databaseHelper.insertData(DatabaseHelper.TABLE_INICIOSESION, contentValues);
                } catch (android.database.SQLException e) {
                    Toast.makeText(LoginActivity.this, LOGIN_ERROR, Toast.LENGTH_SHORT).show();
                }

                SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
                boolean pagado = prefs.getBoolean(PAGADO, false);

                if (pagado) {
                    navigateToMainActivity();
                } else {
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putBoolean(PAGADO, true);
                    editor.apply();

                    Toast.makeText(LoginActivity.this, LOGIN_SUCCESS, Toast.LENGTH_SHORT).show();
                    navigateToMainActivity();
                }
            } else {
                Toast.makeText(LoginActivity.this, INVALID_CREDENTIALS, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(LoginActivity.this, RegistroActivity.class);
        startActivity(intent);
        finish();
    }
}