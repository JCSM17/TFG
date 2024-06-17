package com.example.tfg.jc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
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
import com.example.tfg.javi.UserData;

public class IntroObjetivoFragment extends Fragment {

    private static final String ERROR_MESSAGE = "Por favor, completa todos los campos correctamente";
    private static final String ERROR_SAVE_DATA = "Error al guardar los datos del usuario";
    private static final String GENDER_DEFAULT = "Género";
    private static final String EMAIL_KEY = "email";
    private int selectedId;
    private String estatura;
    private String edad;
    private String genero;
    private String peso;

    private DatabaseHelper databaseHelper;
    private FragmentObjetivoBinding binding;

    public IntroObjetivoFragment() {
        // Constructor vacío
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentObjetivoBinding.inflate(inflater, container, false);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.gender_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.generoSpinner.setAdapter(adapter);

        // Asigna los valores a las variables de instancia aquí
        selectedId = binding.radioObjetivo.getCheckedRadioButtonId();
        estatura = binding.estaturaInput.getText().toString();
        edad = binding.aniosInput.getText().toString();
        genero = binding.generoSpinner.getSelectedItem().toString();
        peso = binding.pesoInput.getText().toString();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        String email = sharedPreferences.getString(EMAIL_KEY, "");

        if (email.isEmpty()) {
            Log.e("IntroObjetivoFragment", "El correo electrónico recuperado es nulo o está vacío");
        } else {
            // Llamar al método saveUserData()
            saveUserData(selectedId, estatura, edad, genero, peso);
        }
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

    private int getUserId(String email) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        int userId = sharedPreferences.getInt("userId", -1);

        if (userId == -1) {
            // Si el ID del usuario no está en las preferencias compartidas, obténlo de la base de datos
            userId = databaseHelper.getUserIdByEmail(email);

            // Guarda el ID del usuario en las preferencias compartidas para uso futuro
            if (userId != -1) {
                sharedPreferences.edit().putInt("userId", userId).apply();
            }
        }

        return userId;
    }

    private float calculateCalories(String genero, float peso, float estatura, int edad, String actividad) {
        float tmb;

        if (genero.equals("Hombre")) {
            tmb = (10 * peso) + (6.25f * estatura) - (5 * edad) + 5;
        } else {
            tmb = (10 * peso) + (6.25f * estatura) - (5 * edad) - 161;
        }

        switch (actividad) {
            case "Sedentario":
                return tmb * 1.2f;
            case "Ligero":
                return tmb * 1.375f;
            case "Moderado":
                return tmb * 1.55f;
            case "Activo":
                return tmb * 1.725f;
            case "Muy activo":
                return tmb * 1.9f;
            default:
                return tmb * 1.2f;
        }
    }

    private void saveUserData(int selectedId, String estatura, String edad, String genero, String peso) {
        try {
            float estaturaFloat = Float.parseFloat(estatura);
            int edadInt = Integer.parseInt(edad);
            float pesoFloat = Float.parseFloat(peso);

            // Guardar la elección del usuario entre "volumen", "definicion" y "recomendado"
            String objetivo;
            if (selectedId == R.id.radioOptionDefinicion) {
                objetivo = "definicion";
            } else if (selectedId == R.id.radioOptionVolumen) {
                objetivo = "volumen";
            } else {
                if (pesoFloat / 100 > estaturaFloat) {
                    objetivo = "definicion";
                } else {
                    objetivo = "volumen";
                }
            }

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            String email = sharedPreferences.getString(EMAIL_KEY, "");
            Log.d("IntroObjetivoFragment", "Correo electrónico recuperado: " + email);

            // Verificar si el correo electrónico está vacío
            if (!email.isEmpty()) {
                // Obtener el id del usuario
                int userId = getUserId(email);

                // Calcular calorías
                String actividad = "Moderado";
                float calorias = calculateCalories(genero, pesoFloat, estaturaFloat, edadInt, actividad);

                // Crear una nueva instancia de UserData con el id del usuario y guardarla en la base de datos
                UserData userData = new UserData(userId, email, selectedId, estaturaFloat, edadInt, genero, pesoFloat, objetivo, calorias);
                long rowId = databaseHelper.insertUserData(userData);

                if (rowId != -1) {
                    Log.d("IntroObjetivoFragment", "Datos del usuario guardados correctamente en la fila ID: " + rowId);

                    // Recuperar los datos del usuario de la base de datos
                    UserData savedUserData = databaseHelper.getUserData(userId);
                    if (savedUserData == null) {
                        Log.e("IntroObjetivoFragment", "No se pudieron recuperar los datos del usuario de la base de datos");

                        // Crear una nueva instancia de UserData con valores predeterminados y guardarla en la base de datos
                        UserData defaultUserData = new UserData(userId, email, selectedId, 0.0f, 0, "Desconocido", 0.0f, "Desconocido", 0.0f);
                        long defaultRowId = databaseHelper.insertUserData(defaultUserData);
                        if (defaultRowId != -1) {
                            Log.d("IntroObjetivoFragment", "Datos predeterminados del usuario guardados correctamente en la fila ID: " + defaultRowId);
                        } else {
                            Log.e("IntroObjetivoFragment", "Error al guardar los datos predeterminados del usuario: " + defaultUserData);
                            showToast(ERROR_SAVE_DATA);
                        }
                    } else {
                        // Imprimir los datos del usuario en el registro para verificar que se guardaron correctamente
                        Log.d("IntroObjetivoFragment", "Datos del usuario recuperados de la base de datos: " + savedUserData);
                    }
                } else {
                    Log.e("IntroObjetivoFragment", "Error al guardar los datos del usuario: " + userData);
                    showToast(ERROR_SAVE_DATA);
                }
            } else {
                // Muestra un mensaje al usuario indicando que el campo de correo electrónico no puede estar vacío
                showToast("El campo de correo electrónico no puede estar vacío");
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
            fragmentTransaction.replace(R.id.fragment_container, tiposCuerpoFragment);
            fragmentTransaction.commit();
        }
    }

    private void showToast(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
