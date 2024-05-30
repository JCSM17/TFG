package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.databinding.ActivityLoginBinding;
import com.example.tfg.jc.MenuActivity;

import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    private static final String PREFS = "PREFS";
    private static final String PAGADO = "pagado";

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.botonIniciarSesion.setOnClickListener(this::handleLogin);
        binding.textoRegistro.setOnClickListener(view -> navigateToFragment(new RegistroFragment()));
    }

    private void navigateToFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container_login, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void handleLogin(View view) {
        String email = binding.entradaEmail.getText().toString();
        String password = binding.entradaContrasenia.getText().toString();

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.empty_password));
        } else {
            Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);
            if (checkCredentials) {
                RegistroData registroData = databaseHelper.getRegistroByEmail(email);
                if (registroData != null) {
                    long currentTimeMillis = System.currentTimeMillis();
                    long subscriptionEndMillis = registroData.getFechaInicioSuscripcion() + registroData.getDuracionSuscripcion() * 24 * 60 * 60 * 1000;
                    if (currentTimeMillis <= subscriptionEndMillis) {
                        // El usuario tiene días de suscripción disponibles
                        // Continúa con el inicio de sesión
                        ContentValues contentValues = new ContentValues();
                        contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
                        try {
                            contentValues.put(DatabaseHelper.COLUMN_PASSWORD, databaseHelper.hashPassword(password));
                        } catch (NoSuchAlgorithmException e) {
                            // Manejar la excepción aquí, por ejemplo, registrar la excepción:
                            Log.e("LoginActivity", "Error al hashear la contraseña", e);
                        }
                        try {
                            databaseHelper.insertData(DatabaseHelper.TABLE_INICIOSESION, contentValues);
                        } catch (android.database.SQLException e) {
                            showToast(getString(R.string.login_error));
                        }

                        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
                        boolean pagado = prefs.getBoolean(PAGADO, false);

                        if (pagado) {
                            navigateToActivity(MenuActivity.class);
                        } else {
                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putBoolean(PAGADO, true);
                            editor.apply();

                            showToast(getString(R.string.login_success));
                            navigateToActivity(MenuActivity.class);
                        }
                    } else {
                        // El usuario no tiene días de suscripción disponibles
                        // Muestra un mensaje al usuario y no permitas el inicio de sesión
                        showToast(getString(R.string.no_subscription_days));
                        navigateToFragment(new PlanesFragment());
                    }
                } else {
                    // El usuario no está registrado
                    // Muestra un mensaje al usuario y no permitas el inicio de sesión
                    showToast(getString(R.string.user_not_registered));
                    navigateToFragment(new RegistroFragment());
                }
            } else {
                showToast(getString(R.string.invalid_credentials));
            }
        }
    }

    private void navigateToActivity(Class<?> cls) {
        Intent intent = new Intent(LoginActivity.this, cls);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}