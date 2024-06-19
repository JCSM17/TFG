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

    private static final String PREFERENCES_NAME = "tfg_preferences";
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
        Intent intent = new Intent(this, FragmentContainerActivity.class);
        intent.putExtra("fragmentName", fragment.getClass().getName());
        Log.d("LoginActivity", "Fragment name: " + fragment.getClass().getName());
        startActivity(intent);
    }

    private void handleLogin(View view) {
        String email = binding.entradaEmail.getText().toString();
        String password = binding.entradaContrasenia.getText().toString();

        Log.d("LoginActivity", "Email ingresado: " + email);
        Log.d("LoginActivity", "Contraseña ingresada: " + password);

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast(getString(R.string.invalid_email));
        } else if (TextUtils.isEmpty(password)) {
            showToast(getString(R.string.empty_password));
        } else {
            String storedPassword = databaseHelper.getPasswordByEmail(email);

            Log.d("LoginActivity", "Contraseña almacenada: " + storedPassword);

            if (storedPassword != null && storedPassword.equals(databaseHelper.hashPassword(password))) {
                navigateToActivity();
            } else {
                String hashedPassword = databaseHelper.hashPassword(password);
                if (hashedPassword == null) {
                    Log.e("LoginActivity", "Error al hashear la contraseña");
                    return;
                }
                Log.d("LoginActivity", "Contraseña hasheada: " + hashedPassword);

                Boolean checkCredentials = databaseHelper.checkEmailPassword(email, hashedPassword);
                Log.d("LoginActivity", "Resultado de checkEmailPassword: " + checkCredentials);

                if (checkCredentials) {
                    RegistroData registroData = databaseHelper.getRegistroByEmail(email);
                    Log.d("LoginActivity", "RegistroData obtenido: " + registroData);

                    if (registroData != null) {
                        navigateToActivity();
                    } else {
                        showToast(getString(R.string.user_not_registered));
                        navigateToFragment(new RegistroFragment());
                    }
                } else {
                    showToast(getString(R.string.invalid_credentials));
                    navigateToFragment(new RegistroFragment());
                }
            }
        }
    }

    private void navigateToActivity() {
        showToast(getString(R.string.login_success));
        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void showToast(String message) {
        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}