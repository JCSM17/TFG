package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class DefinicionLunesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_LUNES = {
            R.id.botonVideoDominadas,
            R.id.botonVideoRemoMancuernas,
            R.id.botonVideoPesoMuertoRumano,
            R.id.botonVideoJalonPronoPecho,
            R.id.botonVideoFacePull,
            R.id.botonVideoCurlBarra
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_LUNES = {
            R.id.imagenDominadas,
            R.id.imagenRemoMancuernas,
            R.id.imagenPesoMuertoRumano,
            R.id.imageJalonPronoPecho,
            R.id.imagenFacePull,
            R.id.imagenCurlBarra
    };

    public DefinicionLunesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_lunes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_lunes);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_LUNES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_DEFINICION_LUNES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckDefinicionLunes);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_LUNES) {
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