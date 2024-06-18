package com.example.tfg.javi;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.databinding.FragmentRegistroBinding;

public class RegistroFragment extends Fragment {

    private static final String USERNAME = "cristianito5689@gmail.com";
    private static final String PASSWORD = "bxvb ccln dqro kynm";
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_KEY = "email";
    FragmentRegistroBinding binding;

    DatabaseHelper databaseHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRegistroBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        databaseHelper = new DatabaseHelper(requireActivity());

        binding.registrarse.setOnClickListener(v -> handleSignupButtonClick());
        binding.volverInicioSesion.setOnClickListener(v -> startLoginActivity());
    }

    private void handleSignupButtonClick() {
        String nombre = binding.nombre.getText().toString();
        String apellido = binding.apellidos.getText().toString();
        String telefono = binding.telefono.getText().toString();
        String email = binding.email.getText().toString();
        String password = binding.password.getText().toString();

        if (!areFieldsValid(nombre, apellido, telefono, email, password)) {
            return;
        }

        registerUser(nombre, apellido, telefono, email, password);
    }

    private boolean areFieldsValid(String nombre, String apellido, String telefono, String email, String password) {
        if (email.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty() || password.isEmpty()) {
            Toast.makeText(requireActivity(), getString(R.string.complete_fields), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireActivity(), getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        boolean checkUser = databaseHelper.checkUserEmail(email);
        if (checkUser) {
            Toast.makeText(requireActivity(), getString(R.string.email_exists), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registerUser(String nombre, String apellido, String telefono, String email, String password) {
        Activity activity = getActivity();
        if (activity == null) {
            return;
        }

        try {
            String hashedPassword = databaseHelper.hashPassword(password); // Hacer hash de la contraseña

            ContentValues contentValues = new ContentValues();
            contentValues.put(DatabaseHelper.COLUMN_EMAIL, email);
            contentValues.put(DatabaseHelper.COLUMN_PASSWORD, hashedPassword); // Guardar la contraseña hasheada
            contentValues.put(DatabaseHelper.COLUMN_NOMBRE, nombre);
            contentValues.put(DatabaseHelper.COLUMN_APELLIDO, apellido);
            contentValues.put(DatabaseHelper.COLUMN_TELEFONO, telefono);
            long userId = databaseHelper.insertData(DatabaseHelper.TABLE_REGISTRO, contentValues);
            if (userId == -1) {
                throw new android.database.SQLException("Failed to insert row");
            }
            sendWelcomeEmail(email);
            Toast.makeText(activity, getString(R.string.registration_success), Toast.LENGTH_SHORT).show();

            // Guardar el correo electrónico y el userId en las preferencias compartidas
            SharedPreferences sharedPreferences = requireContext().getSharedPreferences("tfg_preferences", Context.MODE_PRIVATE);
            sharedPreferences.edit().putString(EMAIL_KEY, email).apply();
            sharedPreferences.edit().putInt("userId", (int) userId).apply();
            Log.d("RegistroFragment", "Correo electrónico y userId guardados: " + email + ", " + userId);

            // Replace the current fragment with PlanesFragment
            PlanesFragment planesFragment = new PlanesFragment();
            Bundle bundle = new Bundle();
            bundle.putLong("userId", userId); // Pass the user id to PlanesFragment
            planesFragment.setArguments(bundle);

            // Clear the back stack
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, planesFragment);
            transaction.commit();

        } catch (android.database.SQLException e) {
            Toast.makeText(activity, getString(R.string.registration_failed), Toast.LENGTH_SHORT).show();
        }
    }

    private void startLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

    private void sendWelcomeEmail(String recipientEmail) {
        new SendEmailTask(getActivity(), recipientEmail, USERNAME, PASSWORD, SMTP_HOST, SMTP_PORT).execute();
    }

    private static class SendEmailTask extends AsyncTask<Void, Void, Boolean> {

        private final Activity activity;
        private final String recipientEmail;
        private final String username;
        private final String password;
        private final String smtpHost;
        private final String smtpPort;

        SendEmailTask(Activity activity, String recipientEmail, String username, String password, String smtpHost, String smtpPort) {
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
                String htmlBody = "<p>" + activity.getString(R.string.welcome_email_body) + "</p>"
                        + "<a href=\"myapp://open/planes\" style=\"background-color: #008CBA; color: white; padding: 15px 32px; text-align: center; text-decoration: none; display: inline-block; font-size: 16px; margin: 4px 2px; cursor: pointer; border: none;\">Ir a elegir el plan</a>";
                sender.sendEmail(recipientEmail, activity.getString(R.string.welcome_email_subject), htmlBody);
                return true;
            } catch (Exception e) {
                Log.e("SendEmailTask", "Error al enviar el correo: ", e);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (activity != null && !result) {
                Toast.makeText(activity, activity.getString(R.string.welcome_email_error), Toast.LENGTH_SHORT).show();
            }
        }
    }
}