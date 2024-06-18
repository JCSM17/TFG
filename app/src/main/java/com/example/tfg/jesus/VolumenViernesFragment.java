package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class VolumenViernesFragment extends Fragment {

    private static final int[] BUTTON_IDS_VOLUMEN_VIERNES = {
            R.id.botonVideoDominadasPronas,
            R.id.botonVideoPesoMuerto,
            R.id.botonVideoPressBancaMancuernas,
            R.id.botonVideoElevacionPolea,
            R.id.botonVideoCurlInclinadoMancuernas,
            R.id.botonVideoExtencionTricepsPolea
    };

    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_VIERNES = {
            R.id.imagenPronas,
            R.id.imagenPesoMuerto,
            R.id.imagenPressBancaMancuernas,
            R.id.imageElevacionPolea,
            R.id.imagenInclinadoMancuernas,
            R.id.imagenExtTricepsPoleaViernes
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_viernes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_viernes);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_VIERNES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_VOLUMEN_VIERNES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckVolumenViernes);

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_VIERNES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
    }
}