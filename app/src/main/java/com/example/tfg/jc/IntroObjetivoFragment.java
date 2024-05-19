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

public class IntroObjetivoFragment extends Fragment {

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

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                String estatura = estaturaInput.getText().toString();
                String edad = edadInput.getText().toString();
                String genero = generoSpinner.getSelectedItem().toString();

                // Validar las entradas del usuario
                if (selectedId == -1 || estatura.isEmpty() || edad.isEmpty() || genero.equals("Género")) {
                    Toast.makeText(getContext(), "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    // Guardar los datos del usuario en las preferencias compartidas
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("selectedId", selectedId);
                    editor.putString("estatura", estatura);
                    editor.putString("edad", edad);
                    editor.putString("genero", genero);
                    editor.apply();

                    // Proceder a la siguiente pantalla
                    TiposCuerpoFragment tiposCuerpoFragment = new TiposCuerpoFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    if (fragmentManager != null) {
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        // Hay qe meter el fragmento de javi anterior         fragmentTransaction.replace(R.id.fragment_container, tiposCuerpoFragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    }
                }
            }
        });

        return view;
    }
}