package com.example.tfg.jc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.tfg.R;
import com.example.tfg.databinding.FragmentObjetivoBinding;
import com.example.tfg.javi.DatabaseHelper;

public class IntroObjetivoFragment extends Fragment {

    private static final String ERROR_MESSAGE = "Por favor, completa todos los campos correctamente";
    private static final String ERROR_SAVE_DATA = "Error al guardar los datos del usuario";
    private static final String GENDER_DEFAULT = "Género";
    private static final String EMAIL_KEY = "email";
    private static final String SELECTED_ID_KEY = "selectedId";
    private static final String OBJETIVO_KEY = "objetivo";
    private static final String ESTATURA_KEY = "estatura";
    private static final String PESO_KEY = "peso";
    private static final String EDAD_KEY = "edad";
    private static final String GENERO_KEY = "genero";

    private DatabaseHelper databaseHelper;
    private ArrayAdapter<CharSequence> adapter;
    private FragmentObjetivoBinding binding; // Añade esta línea

    public IntroObjetivoFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentObjetivoBinding binding = FragmentObjetivoBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.nextButton.setOnClickListener(v -> {
            int selectedId = binding.radioObjetivo.getCheckedRadioButtonId();
            String estatura = binding.estaturaInput.getText().toString();
            String edad = binding.aniosInput.getText().toString();
            String genero = binding.generoSpinner.getSelectedItem().toString();
            String peso = binding.pesoInput.getText().toString();

            if (!validateInputs(selectedId, estatura, edad, genero, peso)) {
                showToast(ERROR_MESSAGE);
            } else {
                saveUserData(selectedId, estatura, edad, genero, peso);
                navigateToNextScreen();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Initialize DatabaseHelper and the adapter for generoSpinner
        databaseHelper = new DatabaseHelper(requireContext());
        adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.generoSpinner.setAdapter(adapter);
    }

    private boolean validateInputs(int selectedId, String estatura, String edad, String genero, String peso) {
        try {
            float estaturaFloat = Float.parseFloat(estatura);
            float pesoFloat = Float.parseFloat(peso);
            if (estaturaFloat <= 0 || pesoFloat <= 0) {
                return false;
            }
        } catch (NumberFormatException e) {
            showToast("Error al convertir los datos del usuario a números");
            return false;
        }

        return selectedId != -1 && !estatura.isEmpty() && !edad.isEmpty() && !genero.equals(GENDER_DEFAULT);
    }

    private void saveUserData(int selectedId, String estatura, String edad, String genero, String peso) {
        try {
            float estaturaFloat = Float.parseFloat(estatura);
            int edadInt = Integer.parseInt(edad);
            float pesoFloat = Float.parseFloat(peso);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(SELECTED_ID_KEY, selectedId);
            editor.putFloat(ESTATURA_KEY, estaturaFloat);
            editor.putInt(EDAD_KEY, edadInt);
            editor.putString(GENERO_KEY, genero);
            editor.putFloat(PESO_KEY, pesoFloat);

            // Guardar la elección del usuario entre "volumen", "definicion" y "recomendado"
            String objetivo;
            if (selectedId == R.id.radioOptionDefinicion) {
                objetivo = "definicion";
            } else if (selectedId == R.id.radioOptionVolumen) {
                objetivo = "volumen";
            } else {
                if (pesoFloat > estaturaFloat) {
                    objetivo = "definicion";
                } else {
                    objetivo = "volumen";
                }
            }
            String email = sharedPreferences.getString(EMAIL_KEY, ""); // Añade esta línea
            boolean success = databaseHelper.insertUserData(email, estaturaFloat, edadInt, genero, pesoFloat, objetivo);
            if (!success) {
                showToast(ERROR_SAVE_DATA);
            }
        } catch (NumberFormatException e) {
            showToast("Error al convertir los datos del usuario a números");
        }
    }

    private void navigateToNextScreen() {
        TiposCuerpoFragment tiposCuerpoFragment = new TiposCuerpoFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
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