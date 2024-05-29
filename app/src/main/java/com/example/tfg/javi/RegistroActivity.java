package com.example.tfg.javi;

import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tfg.databinding.ActivityRegistroBinding;

public class RegistroActivity extends AppCompatActivity {

    private static final String USERNAME = "cristianito5689@gmail.com";
    private static final String PASSWORD = "bxvb ccln dqro kynm";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";

    ActivityRegistroBinding binding;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistroBinding.inflate(getLayoutInflater());
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
            Toast.makeText(RegistroActivity.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(RegistroActivity.this, "Por favor, introduce una dirección de correo electrónico válida", Toast.LENGTH_SHORT).show();
        } else {
            Boolean checkUser = databaseHelper.checkUserEmail(email);
            if (checkUser) {
                Toast.makeText(RegistroActivity.this, "Ya existe una cuenta con este correo electrónico", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
                    contentValues.put(DatabaseHelper.COLUMN_PASSWORD, password);
                    contentValues.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
                    contentValues.put(DatabaseHelper.COLUMN_APELLIDO, apellido);
                    contentValues.put(DatabaseHelper.COLUMN_TELEFONO, telefono);
                    databaseHelper.insertData(DatabaseHelper.TABLE_REGISTRO, contentValues);
                    sendWelcomeEmail(email);
                    Toast.makeText(RegistroActivity.this, "¡RegistroData exitoso! Bienvenido", Toast.LENGTH_SHORT).show();
                    startNewActivity(LoginActivity.class);
                    finish();
                } catch (android.database.SQLException e) {
                    Toast.makeText(RegistroActivity.this, "El registro ha fallado", Toast.LENGTH_SHORT).show();
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

        private final RegistroActivity activity;
        private final String recipientEmail;
        private final String username;
        private final String password;
        private final String smtpHost;
        private final String smtpPort;

        SendEmailTask(RegistroActivity activity, String recipientEmail, String username, String password, String smtpHost, String smtpPort) {
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