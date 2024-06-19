package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class AbdominalesFragment extends Fragment {

    private static final int[] BUTTON_IDS_ABDOMINALES = {
            R.id.botonVideoRuedaAbsPie,
            R.id.botonVideoDragonFlag,
            R.id.botonVideoStirThePot,
            R.id.botonVideoGirosRusosBarra
    };
    private static final int[] IMAGE_BUTTON_IDS_ABDOMINALES = {
            R.id.imagenRuedaAbsPie,
            R.id.imagenDragonFlag,
            R.id.imagenStirThePot,
            R.id.imagenGirosRusosBarra
    };

    public AbdominalesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_miercoles_abdominales, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_abdominales);

        for (int i = 0; i < BUTTON_IDS_ABDOMINALES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_ABDOMINALES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckAbdominales);

        for (int id : IMAGE_BUTTON_IDS_ABDOMINALES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
        // Crea una nueva instancia de CountdownFragment
        CountdownFragment countdownFragment = new CountdownFragment();

        // Usa el ChildFragmentManager para agregar el fragmento hijo
        getChildFragmentManager().beginTransaction()
                .replace(R.id.cronometro_fragment_container, countdownFragment)
                .commit();
    }
}