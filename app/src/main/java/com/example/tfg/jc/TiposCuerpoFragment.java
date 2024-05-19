package com.example.tfg.jc;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class TiposCuerpoFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tipos_cuerpo, container, false);

        RadioButton bodyType1Button = view.findViewById(R.id.botonTipoEctomorfo);
        RadioButton bodyType2Button = view.findViewById(R.id.botonTipoMesomorfo);
        RadioButton bodyType3Button = view.findViewById(R.id.botonTipoEndomorfo);
        Button nextButton = view.findViewById(R.id.nextButton);

        bodyType1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBodyType("type1");
                // Aquí necesitarás implementar la lógica para cambiar a otro Fragment o Activity
            }
        });

        bodyType2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBodyType("type2");
                // Aquí necesitarás implementar la lógica para cambiar a otro Fragment o Activity
            }
        });

        bodyType3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBodyType("type3");
                // Aquí necesitarás implementar la lógica para cambiar a otro Fragment o Activity
            }
        });

        return view;
    }

    private void saveBodyType(String type) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("bodyType", type);
        editor.apply();
    }
}