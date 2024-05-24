package com.example.tfg.jc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

public class TiposCuerpoFragment extends Fragment {

    private static final String BODY_TYPE_KEY = "bodyType";
    private static final String EMAIL_KEY = "email";
    private static final String PREFERENCES_NAME = "com.example.tfg.preferences";

    private String ERROR_EMAIL_NOT_FOUND;
    private String ERROR_BODY_TYPE_NOT_SELECTED;
    private String SELECT_BODY_TYPE_MESSAGE;

    private DatabaseHelper databaseHelper;
    private String bodyType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipos_cuerpo, container, false);

        ERROR_EMAIL_NOT_FOUND = getString(R.string.error_email_not_found);
        ERROR_BODY_TYPE_NOT_SELECTED = getString(R.string.error_body_type_not_selected);
        SELECT_BODY_TYPE_MESSAGE = getString(R.string.select_body_type_message);

        RadioGroup bodyTypeGroup = view.findViewById(R.id.bodyTypeRadioGroup);
        Button nextButton = view.findViewById(R.id.nextButton);

        databaseHelper = new DatabaseHelper(getContext());

        bodyTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.botonTipoEctomorfo) {
                bodyType = getString(R.string.ectomorfo);
            } else if (checkedId == R.id.botonTipoMesomorfo) {
                bodyType = getString(R.string.mesomorfo);
            } else if (checkedId == R.id.botonTipoEndomorfo) {
                bodyType = getString(R.string.endomorfo);
            }
        });

        nextButton.setOnClickListener(v -> {
            if (bodyType != null) {
                saveBodyType(bodyType);
                navigateToNextScreen();
            } else {
                showToast(SELECT_BODY_TYPE_MESSAGE);
            }
        });

        return view;
    }


    private void saveBodyType(String type) {
        Context context = getContext();
        if (context != null && type != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(BODY_TYPE_KEY, type);
            editor.apply();

            // Guardar el tipo de cuerpo del usuario en la base de datos
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            if (!email.isEmpty()) {
                databaseHelper.updateUserBodyType(email, type);
            } else {
                // Manejar el caso en el que el email no esté presente
                showToast(ERROR_EMAIL_NOT_FOUND);
            }
        } else {
            // Manejar el caso en el que el tipo de cuerpo no esté presente
            showToast(ERROR_BODY_TYPE_NOT_SELECTED);
        }
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}