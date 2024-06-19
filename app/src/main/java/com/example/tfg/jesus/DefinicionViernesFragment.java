package com.example.tfg.jesus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class DefinicionViernesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_VIERNES = {
            R.id.botonVideoPesoMuertoViernes,
            R.id.botonVideoZancadaEstaticaMultipower,
            R.id.botonVideoPrensaInclinada,
            R.id.botonVideoPatadaTricepsPolea,
            R.id.botonVideoCurlBicepsAlternoSentadoMancuernas,
            R.id.botonVideoExtencionTricepsOverhead
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_VIERNES = {
            R.id.imagenPesoMuertoViernes,
            R.id.imagenZancadasEstaticasMultipower,
            R.id.imagenPrensaInclinada,
            R.id.imagenPatadaTricepsPolea,
            R.id.imagenCurlBicepsAlternoSentadoMancuernas,
            R.id.imagenExtencionTricepsOverhead
    };

    public DefinicionViernesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_viernes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_viernes);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_VIERNES.length; i++) {
            ButtonSetupUtils.setupButton(this, view, BUTTON_IDS_DEFINICION_VIERNES[i], urls[i]);
        }

        ButtonSetupUtils.setupButton(this, view, R.id.imagenCheckDefinicionViernes);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_VIERNES) {
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