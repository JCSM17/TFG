package com.example.tfg.jc;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

public class TiposCuerpoFragment extends Fragment {

    private static final String TAG = "TiposCuerpoFragment";
    private static final String SELECTED_RADIO_BUTTON_ID_KEY = "selectedRadioButtonId";
    private static final String EMAIL_KEY = "email";
    private static final String PREFERENCES_NAME = "com.example.tfg.preferences";

    private String ERROR_EMAIL_NOT_FOUND;
    private String ERROR_BODY_TYPE_NOT_SELECTED;
    private String SELECT_BODY_TYPE_MESSAGE;

    private DatabaseHelper databaseHelper;
    private int selectedRadioButtonId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipos_cuerpo, container, false);

        ERROR_EMAIL_NOT_FOUND = getString(R.string.error_email_not_found);
        ERROR_BODY_TYPE_NOT_SELECTED = getString(R.string.error_body_type_not_selected);
        SELECT_BODY_TYPE_MESSAGE = getString(R.string.select_body_type_message);

        RadioButton botonTipoEctomorfo = view.findViewById(R.id.botonTipoEctomorfo);
        RadioButton botonTipoMesomorfo = view.findViewById(R.id.botonTipoMesomorfo);
        RadioButton botonTipoEndomorfo = view.findViewById(R.id.botonTipoEndomorfo);
        Button nextButton = view.findViewById(R.id.botonFinalizar);

        databaseHelper = new DatabaseHelper(getContext());

        // Restaura el botón de opción seleccionado desde las preferencias compartidas
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            selectedRadioButtonId = sharedPreferences.getInt(SELECTED_RADIO_BUTTON_ID_KEY, -1);
            Log.d(TAG, "Restaurado ID botón seleccionado: " + selectedRadioButtonId);
        } else {
            Log.e(TAG, "Error: Context is null");
            // Aquí puedes manejar el caso en que context es null
        }

        Log.d(TAG, "Restaurado ID botón seleccionado: " + selectedRadioButtonId);

        if (selectedRadioButtonId == botonTipoEctomorfo.getId()) {
            botonTipoEctomorfo.setChecked(true);
        } else if (selectedRadioButtonId == botonTipoMesomorfo.getId()) {
            botonTipoMesomorfo.setChecked(true);
        } else if (selectedRadioButtonId == botonTipoEndomorfo.getId()) {
            botonTipoEndomorfo.setChecked(true);
        }

        botonTipoEctomorfo.setOnClickListener(v -> {
            botonTipoEctomorfo.setChecked(true);
            botonTipoMesomorfo.setChecked(false);
            botonTipoEndomorfo.setChecked(false);
            selectedRadioButtonId = R.id.botonTipoEctomorfo;
        });

        botonTipoMesomorfo.setOnClickListener(v -> {
            botonTipoEctomorfo.setChecked(false);
            botonTipoMesomorfo.setChecked(true);
            botonTipoEndomorfo.setChecked(false);
            selectedRadioButtonId = R.id.botonTipoMesomorfo;
        });

        botonTipoEndomorfo.setOnClickListener(v -> {
            botonTipoEctomorfo.setChecked(false);
            botonTipoMesomorfo.setChecked(false);
            botonTipoEndomorfo.setChecked(true);
            selectedRadioButtonId = R.id.botonTipoEndomorfo;
        });

        nextButton.setOnClickListener(v -> {
            Log.d(TAG, "Botón seleccionado ID al hacer clic: " + selectedRadioButtonId);
            if (selectedRadioButtonId != -1) {
                saveBodyType(selectedRadioButtonId);
                navigateToNextScreen();
            } else {
                showToast(SELECT_BODY_TYPE_MESSAGE);
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_RADIO_BUTTON_ID_KEY, selectedRadioButtonId);
    }

    private void saveBodyType(int selectedId) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SELECTED_RADIO_BUTTON_ID_KEY, selectedId);
            editor.apply();

            String bodyType = null;
            if (selectedId == R.id.botonTipoEctomorfo) {
                bodyType = getString(R.string.ectomorfo);
            } else if (selectedId == R.id.botonTipoMesomorfo) {
                bodyType = getString(R.string.mesomorfo);
            } else if (selectedId == R.id.botonTipoEndomorfo) {
                bodyType = getString(R.string.endomorfo);
            }

            // Obtener el id del usuario
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            int userId = databaseHelper.getUserId(email); // Obtén el ID del usuario usando el correo electrónico

            if (userId != -1 && bodyType != null) {
                Log.d(TAG, "UserId: " + userId + ", Body Type: " + bodyType);
                databaseHelper.updateUserBodyType(String.valueOf(userId), bodyType);
            } else {
                Log.e(TAG, "Error: UserId not found or body type not selected");
                showToast(ERROR_EMAIL_NOT_FOUND);
            }
        } else {
            Log.e(TAG, "Error: Context is null");
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
