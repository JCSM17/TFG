package com.example.tfg.javi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {

    ActivitySignupBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String confirmPassword = binding.signupConfirm.getText().toString();
                String name = binding.signupName.getText().toString();
                String lastName = binding.signupLastname.getText().toString();
                String phoneNumber = binding.signupPhonenumber.getText().toString();

                if (email.isEmpty() || !isValidEmail(email)) {
                    Toast.makeText(SignupActivity.this, "Introduce un correo válido (@gmail, @outlook, @yahoo)", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Por favor, introduzca su contraseña", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                } else if (name.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Por favor, introduzca su nombre", Toast.LENGTH_SHORT).show();
                } else if (lastName.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Por favor, introduzca sus apellidos", Toast.LENGTH_SHORT).show();
                } else if (!isValidName(name)) {
                    Toast.makeText(SignupActivity.this, "El nombre solo puede contener letras", Toast.LENGTH_SHORT).show();
                } else if (!isValidName(lastName)) {
                    Toast.makeText(SignupActivity.this, "Los apellidos solo pueden contener letras", Toast.LENGTH_SHORT).show();
                } else if (!isValidPhoneNumber(phoneNumber)) {
                    Toast.makeText(SignupActivity.this, "Por favor, introduce un número de teléfono válido", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUserEmail = databaseHelper.checkEmail(email);

                    if (!checkUserEmail) {
                        Boolean insert = databaseHelper.insertData(email, password, name, lastName, phoneNumber);

                        if (insert) {
                            Toast.makeText(SignupActivity.this, "¡Te registraste exitosamente!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(SignupActivity.this, "¡Registro fallido!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "¡El usuario ya existe! Por favor, inicia sesión", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        binding.loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isValidEmail(String email) {
        // Expresión regular para validar las direcciones de correo electrónico de Gmail, Outlook y Yahoo
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:gmail|outlook|yahoo)\\.(?:com|es|net|org|edu|gov|info|biz|co\\.uk)$";
        return email.matches(emailPattern);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        // Expresión regular para validar que el número de teléfono contiene solo números y tiene un máximo de 9 dígitos
        String phonePattern = "^[0-9]{1,9}$";
        return phoneNumber.matches(phonePattern);
    }

    private boolean isValidName(String name) {
        // Expresión regular para validar que el nombre solo contiene letras
        return name.matches("[a-zA-Z]+");
    }
}
