package com.example.tfg.jc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.javi.DatabaseHelper;

public class IntroObjetivoFragment extends Fragment {

    private static final String ERROR_MESSAGE = "Por favor, completa todos los campos correctamente";
    private static final String ERROR_SAVE_DATA = "Error al guardar los datos del usuario";
    private static final String GENDER_DEFAULT = "Género";
    private static final String EMAIL_KEY = "email";
    private static final String SELECTED_ID_KEY = "selectedId";
    private static final String ESTATURA_KEY = "estatura";
    private static final String EDAD_KEY = "edad";
    private static final String GENERO_KEY = "genero";

    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_objetivo, container, false);

        RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        EditText estaturaInput = view.findViewById(R.id.estaturaInput);
        EditText edadInput = view.findViewById(R.id.aniosInput);
        Spinner generoSpinner = view.findViewById(R.id.generoSpinner);
        Button nextButton = view.findViewById(R.id.nextButton);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        generoSpinner.setAdapter(adapter);

        // Inicializa DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        nextButton.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String estatura = estaturaInput.getText().toString();
            String edad = edadInput.getText().toString();
            String genero = generoSpinner.getSelectedItem().toString();

            if (!validateInputs(selectedId, estatura, edad, genero)) {
                showToast(ERROR_MESSAGE);
            } else {
                saveUserData(selectedId, estatura, edad, genero);
                navigateToNextScreen();
            }
        });

        return view;
    }

    private boolean validateInputs(int selectedId, String estatura, String edad, String genero) {
        // Aquí puedes agregar más validaciones a los datos del usuario
        return selectedId != -1 && !estatura.isEmpty() && !edad.isEmpty() && !genero.equals(GENDER_DEFAULT);
    }

    private void saveUserData(int selectedId, String estatura, String edad, String genero) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(SELECTED_ID_KEY, selectedId);
        editor.putString(ESTATURA_KEY, estatura);
        editor.putString(EDAD_KEY, edad);
        editor.putString(GENERO_KEY, genero);
        editor.apply();

        String email = sharedPreferences.getString(EMAIL_KEY, "");
        boolean success = databaseHelper.insertUserData(email, estatura, edad, genero);
        if (!success) {
            showToast(ERROR_SAVE_DATA);
        }
    }

    private void navigateToNextScreen() {
        TiposCuerpoFragment tiposCuerpoFragment = new TiposCuerpoFragment();
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container_objetivo, tiposCuerpoFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}