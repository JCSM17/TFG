package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class VolumenLunesFragment extends Fragment {

    private static final int[] BUTTON_IDS_VOLUMEN_LUNES = {
            R.id.botonVideoRemoMancuernaVolumen,
            R.id.botonVideoJalonUnilateralSentado,
            R.id.botonVideoRemoNeutroPoleaVolumen,
            R.id.buttonVideoCurlBicepsBarraZcott,
            R.id.botonVideoCurlPoleaAlta
    };
    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_LUNES = {
            R.id.imagenRemoMancuernaVolumen,
            R.id.imagenJalonUnilateralSentado,
            R.id.imagenRemoNeutroPoleaVolumen,
            R.id.imagenCurlBicepsBarraZcott,
            R.id.imagenCurlPoleaAlta
    };

    public VolumenLunesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_lunes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_lunes);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_LUNES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_VOLUMEN_LUNES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckVolumenLunes);

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_LUNES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
    }
}