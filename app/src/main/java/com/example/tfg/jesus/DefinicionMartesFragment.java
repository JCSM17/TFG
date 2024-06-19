package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class DefinicionMartesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_MARTES = {
            R.id.botonVideoPressMilitarBarra,
            R.id.botonVideoJalonNeutroPecho,
            R.id.botonVideoPressPlanoMancuernas,
            R.id.botonVideoCrucePoleaBajaUnilateral,
            R.id.botonVideoRemoNeutroPolea,
            R.id.botonVideoLateralesPoleaTorso
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_MARTES = {
            R.id.imagenPressMilitarBarra,
            R.id.imagenJalonNeutroPecho,
            R.id.imagenPressPlanoMancuernas,
            R.id.imagenCrucePoleaBajaUnilateral,
            R.id.imagenRemoNeutroPolea,
            R.id.imagenLateralesPoleaTorso
    };

    public DefinicionMartesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_martes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_martes);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_MARTES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_DEFINICION_MARTES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckDefinicionMartes);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_MARTES) {
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