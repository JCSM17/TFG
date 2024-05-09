package com.example.loginsign.jc;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.loginsign.R;

public class HeaderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_header, container, false);

        ImageButton optionsButton = view.findViewById(R.id.optionsButton);
        TextView appName = view.findViewById(R.id.appName);
        ImageButton settingsButton = view.findViewById(R.id.settingsButton);

        // Configurar el listener para el botón de opciones
        optionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir qué acción se debe realizar cuando se hace clic en el botón de opciones
            }
        });

        // Configurar el listener para el botón de ajustes
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes definir qué acción se debe realizar cuando se hace clic en el botón de ajustes
            }
        });

        return view;
    }
}