package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class DefinicionJuevesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_JUEVES = {
            R.id.botonVideoPressBancaPlano,
            R.id.botonVideoSentadilla,
            R.id.botonVideoPressmilitarmancuerna,
            R.id.botonVideoPressInclinadoMancuerna,
            R.id.botonVideoCrucePoleaBaja,
            R.id.botonVideoelevacionlateral
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_JUEVES = {
            R.id.imagenPressBancaPlano,
            R.id.imagenSentadilla,
            R.id.imagenPressMilitarMancuerna,
            R.id.imagenPressInclinadoMancuerna,
            R.id.imagenCrucePoleaBaja,
            R.id.imagenElevacionLateral,
    };
    
    public DefinicionJuevesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_jueves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_jueves);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_JUEVES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_DEFINICION_JUEVES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckDefinicionJueves);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_JUEVES) {
            ButtonSetupUtils.setupImageButton(view, id);
        }
        // Crea una nueva instancia de CountdownFragment
        CountdownFragment countdownFragment = new CountdownFragment();

        // Usa el ChildFragmentManager para agregar el fragmento hijo
        getChildFragmentManager().beginTransaction()
                .replace(R.id.child_fragment_container, countdownFragment)
                .commit();
    }
}