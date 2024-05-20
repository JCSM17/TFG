package com.example.tfg.javi;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.databinding.ActivitySignUpBinding;

import javax.mail.MessagingException;

public class SignupActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.signupEmail.getText().toString();
                String password = binding.signupPassword.getText().toString();
                String nombre = binding.signupName.getText().toString();
                String apellido = binding.signupLastname.getText().toString();
                String telefono = binding.signupPhonenumber.getText().toString();

                // Verifica si todos los campos están llenos
                if (email.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(SignupActivity.this, "Por favor, introduce una dirección de correo electrónico válida", Toast.LENGTH_SHORT).show();
                } else {
                    Boolean checkUser = databaseHelper.checkEmail(email);
                    if (checkUser) {
                        Toast.makeText(SignupActivity.this, "Ya existe una cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            databaseHelper.insertData(email, password, nombre, apellido, telefono);
                            // Enviar correo de bienvenida
                            sendWelcomeEmail(email);

                            Toast.makeText(SignupActivity.this, "¡Registro exitoso! Bienvenido", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish(); // Finaliza la actividad actual para que no pueda volver atrás
                        } catch (android.database.SQLException e) {
                            Toast.makeText(SignupActivity.this, "El registro ha fallado", Toast.LENGTH_SHORT).show();
                        }
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

    private void sendWelcomeEmail(String recipientEmail) {
        // Usa las credenciales de una cuenta de correo válida
        final String username = "cristianito5689@gmail.com";  // Cambia esto por tu correo
        final String password = "bxvb ccln dqro kynm"; // Cambia esto por tu contraseña de aplicación
        // Contraseña 2 pasos "bxvb ccln dqro kynm"
        final String smtpHost = "smtp.gmail.com";      // Host SMTP de Gmail
        final String smtpPort = "587";                 // Puerto SMTP de Gmail

        new SendEmailTask(this, recipientEmail, username, password, smtpHost, smtpPort).execute();
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Boolean> {

        private final SignupActivity activity;
        private final String recipientEmail;
        private final String username;
        private final String password;
        private final String smtpHost;
        private final String smtpPort;

        SendEmailTask(SignupActivity activity, String recipientEmail, String username, String password, String smtpHost, String smtpPort) {
            this.activity = activity;
            this.recipientEmail = recipientEmail;
            this.username = username;
            this.password = password;
            this.smtpHost = smtpHost;
            this.smtpPort = smtpPort;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            GmailSender sender = new GmailSender(username, password, smtpHost, smtpPort);
            try {
                sender.sendEmail(recipientEmail, "Bienvenido a nuestra App", "¡Gracias por registrarte en nuestra aplicación!");
                return true;
            } catch (MessagingException e) {
                Log.e("SendEmailTask", "Error al enviar el correo: ", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                // No se pudo enviar el correo
                Toast.makeText(activity, "No se pudo enviar el correo de bienvenida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}