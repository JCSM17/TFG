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

    private static final String USERNAME = "cristianito5689@gmail.com";
    private static final String PASSWORD = "bxvb ccln dqro kynm";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    ActivitySignUpBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        databaseHelper = new DatabaseHelper(this);

        binding.signupButton.setOnClickListener(view -> handleSignupButtonClick());

        binding.loginRedirectText.setOnClickListener(view -> startNewActivity(LoginActivity.class));
    }

    private void handleSignupButtonClick() {
        String email = binding.signupEmail.getText().toString();
        String password = binding.signupPassword.getText().toString();
        String nombre = binding.signupName.getText().toString();
        String apellido = binding.signupLastname.getText().toString();
        String telefono = binding.signupPhonenumber.getText().toString();

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
                    sendWelcomeEmail(email);
                    Toast.makeText(SignupActivity.this, "¡Registro exitoso! Bienvenido", Toast.LENGTH_SHORT).show();
                    startNewActivity(LoginActivity.class);
                    finish();
                } catch (android.database.SQLException e) {
                    Toast.makeText(SignupActivity.this, "El registro ha fallado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void startNewActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }

    private void sendWelcomeEmail(String recipientEmail) {
        new SendEmailTask(this, recipientEmail, USERNAME, PASSWORD, SMTP_HOST, SMTP_PORT).execute();
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
            } catch (Exception e) {
                Log.e("SendEmailTask", "Error al enviar el correo: ", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(activity, "No se pudo enviar el correo de bienvenida", Toast.LENGTH_SHORT).show();
            }
        }
    }
}