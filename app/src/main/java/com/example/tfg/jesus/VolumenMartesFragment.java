package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class VolumenMartesFragment extends Fragment {

    public VolumenMartesFragment() {
        // Constructor vacío requerido
    }

    private static final int[] BUTTON_IDS_VOLUMEN_MARTES = {
            R.id.botonVideoSentadillaConBarra,
            R.id.botonVideoPressMilitar,
            R.id.botonVideoPrensaInclinadaa,
            R.id.botonVideoElevacionesLateralesMancuerna,
            R.id.botonVideoCurlIsquiosTumbados,
            R.id.botonVideoFacepull
    };

    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_MARTES = {
            R.id.imagenSentadillaConBarra,
            R.id.imagenPressMilitar,
            R.id.imagenPrensaInclinadaa,
            R.id.imagenElevacionesLateralesMancuerna,
            R.id.imagenCurlIsquiosTumbados,
            R.id.imagenFacepull
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_martes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_martes);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_MARTES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_VOLUMEN_MARTES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckVolumenMartes);

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_MARTES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
    }
}