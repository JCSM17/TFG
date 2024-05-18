package com.example.tfg.jesus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.jc.MenuActivity;
import com.example.tfg.jc.YoutubeUtils;

public class VolumenViernesFragment extends Fragment {

    // Array de IDs de botones
    private final int[] button_ids_volumen_viernes = {
            R.id.botonVideoDominadasPronas,
            R.id.botonVideoPesoMuerto,
            R.id.botonVideoPressBancaMancuernas,
            R.id.botonVideoElevacionPolea,
            R.id.botonVideoCurlInclinadoMancuernas,
            R.id.botonVideoExtencionTricepsPolea
    };

    // Handler para detener la animación
    private final Handler handler = new Handler();

    // Constructor vacío requerido para instanciar el fragmento
    public VolumenViernesFragment() {
    }

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_volumen_viernes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_viernes, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el array de URLs desde strings.xml
        String[] urls = getResources().getStringArray(R.array.urls_volumen_viernes);

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (int i = 0; i < button_ids_volumen_viernes.length; i++) {
            Button button = view.findViewById(button_ids_volumen_viernes[i]);
            int finalI = i; // Variable final para ser usada en la lambda
            button.setOnClickListener(v -> YoutubeUtils.openYoutubeVideo(getContext(), urls[finalI]));
        }

        ImageButton imagenCheckVolumenViernes = view.findViewById(R.id.imagenCheckVolumenViernes);
        imagenCheckVolumenViernes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
        });

        //REPASA LOS IDS DE LAS IMAGENES
        // Array de lista de IDs de las ImageView a las que quieres aplicar la animación
        int[] imageButton_ids_volumen_viernes = {
                R.id.imagenPronas,
                R.id.imagenPesoMuerto,
                R.id.imagenPressBancaMancuernas,
                R.id.imageElevacionPolea,
                R.id.imagenInclinadoMancuernas,
                R.id.imagenExtTricepsPoleaViernes
        };

        // Configurar un listener para cada ImageButton. Cuando se hace clic en un ImageButton, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageButton_ids_volumen_viernes) {
            ImageButton imageButton = view.findViewById(id);
            imageButton.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                imageButton.startAnimation(animation);

                handler.postDelayed(() -> imageButton.clearAnimation(), 3000);
            });
        }
    }
}