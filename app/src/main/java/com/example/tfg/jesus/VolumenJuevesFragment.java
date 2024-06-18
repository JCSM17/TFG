package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class VolumenJuevesFragment extends Fragment {

    private static final int[] BUTTON_IDS_VOLUMEN_JUEVES = {
            R.id.botonVideoPressBanca,
            R.id.botonVideoFondos,
            R.id.botonVideoPolea,
            R.id.buttonFrancesMancuerna,
            R.id.botonVideoExtTriceps,
            R.id.botonVideoPressPalof
    };
    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_JUEVES = {
            R.id.imagenPBP,
            R.id.imagenFondos,
            R.id.imagenCruces,
            R.id.imagePressFrances,
            R.id.imagenExtTriceps,
            R.id.imagenPalof
    };

    public VolumenJuevesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_jueves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_jueves);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_JUEVES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_VOLUMEN_JUEVES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckVolumenJueves);

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_JUEVES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
    }
}