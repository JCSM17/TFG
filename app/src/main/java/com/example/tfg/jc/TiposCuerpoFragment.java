package com.example.tfg.jc;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

import com.example.tfg.javi.DatabaseHelper;

public class TiposCuerpoFragment extends Fragment {

    private static final String BODY_TYPE_KEY = "bodyType";
    private static final String EMAIL_KEY = "email";

    private DatabaseHelper databaseHelper;
    private String bodyType;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipos_cuerpo, container, false);

        RadioGroup bodyTypeGroup = view.findViewById(R.id.bodyTypeRadioGroup);
        Button nextButton = view.findViewById(R.id.nextButton);

        // Inicializa DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        bodyTypeGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.botonTipoEctomorfo) {
                    bodyType = "Ectomorfo";
                } else if (checkedId == R.id.botonTipoMesomorfo) {
                    bodyType = "Mesomorfo";
                } else if (checkedId == R.id.botonTipoEndomorfo) {
                    bodyType = "Endomorfo";
                }
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bodyType != null) {
                    saveBodyType(bodyType);
                    navigateToNextScreen();
                } else {
                    Toast.makeText(getContext(), "Por favor, selecciona un tipo de cuerpo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void saveBodyType(String type) {
        if (type != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(BODY_TYPE_KEY, type);
            editor.apply();

            // Guardar el tipo de cuerpo del usuario en la base de datos
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            if (!email.isEmpty()) {
                databaseHelper.updateUserBodyType(email, type);
            } else {
                // Manejar el caso en el que el email no esté presente
                Toast.makeText(getContext(), "Error: Email no encontrado", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Manejar el caso en el que el tipo de cuerpo no esté presente
            Toast.makeText(getContext(), "Error: Tipo de cuerpo no seleccionado", Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToNextScreen() {
        Intent intent = new Intent(getActivity(), MenuActivity.class);
        startActivity(intent);
    }
}