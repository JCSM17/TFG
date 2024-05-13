package com.example.tfg.javi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.databinding.ActivityLoginBinding;
import com.example.tfg.javi.MainActivity;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.loginEmail.getText().toString();
                String password = binding.loginPassword.getText().toString();

                // Verifica si el campo de correo electrónico está vacío o si el formato no es válido
                if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(LoginActivity.this, "Por favor, introduce una dirección de correo electrónico válida", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Por favor, introduzca su contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkCredentials = databaseHelper.checkEmailPassword(email, password);

                    if (checkCredentials) {
                        // Insertar datos de inicio de sesión en la tabla iniciosesion
                        boolean insertSuccess = databaseHelper.insertData(email, password);

                        if (insertSuccess) {
                            // Verificar si el usuario ya ha pagado
                            SharedPreferences prefs = getSharedPreferences("PREFS", MODE_PRIVATE);
                            boolean pagado = prefs.getBoolean("pagado", false);

                            if (pagado) {
                                // Si ya ha pagado, ir directamente a la actividad principal
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish(); // Finaliza la actividad actual para que no pueda volver atrás
                            } else {
                                // Si no ha pagado, marcar como pagado y luego ir a la actividad principal
                                SharedPreferences.Editor editor = prefs.edit();
                                editor.putBoolean("pagado", true);
                                editor.apply();

                                Toast.makeText(LoginActivity.this, "¡Has iniciado sesión exitosamente!", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "Error al insertar datos de inicio de sesión", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Credenciales no válidas", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, com.example.tfg.javi.SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
