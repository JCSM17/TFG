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
    private static final String PREFERENCES_NAME = "tfg_preferences";
    private String ERROR_BODY_TYPE_NOT_SELECTED;
    private String SELECT_BODY_TYPE_MESSAGE;

    private DatabaseHelper databaseHelper;
    private int selectedRadioButtonId = -1;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipos_cuerpo, container, false);

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
                boolean success = saveBodyType(selectedRadioButtonId);
                if (success) {
                    navigateToNextScreen();
                }
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

    private boolean saveBodyType(int selectedId) {
        Context context = getContext();
        if (context != null) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
            int userId = sharedPreferences.getInt("userId", -1); // Obtén el ID del usuario usando las preferencias compartidas

            // Si userId es -1, intenta obtenerlo de la base de datos
            if (userId == -1) {
                String email = sharedPreferences.getString("email", null);
                if (email != null) {
                    userId = databaseHelper.getUserId(email);
                }
            }

            // Verificar que se haya seleccionado un tipo de cuerpo
            if (selectedId == -1) {
                Log.e(TAG, "Error: No body type selected");
                showToast("Error: No body type selected");
                return false;
            }

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

            if (userId != -1 && bodyType != null) {
                Log.d(TAG, "UserId: " + userId + ", Body Type: " + bodyType);
                databaseHelper.updateUserBodyType(userId, bodyType);
                return true;
            } else {
                Log.e(TAG, "Error: UserId not found or body type not selected");
                showToast("Error: UserId not found or body type not selected");
                return false;
            }
        } else {
            Log.e(TAG, "Error: Context is null");
            showToast(ERROR_BODY_TYPE_NOT_SELECTED);
            return false;
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
