package com.example.tfg.jesus;

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
import com.example.tfg.jc.YoutubeUtils;

import java.util.HashMap;
import java.util.Map;

public class DefinicionLunesFragment extends Fragment {

    // Constructor vacío requerido para instanciar el fragmento
    public DefinicionLunesFragment() {
    }

    // Array de IDs de botones
    private final int[] button_ids_definicion_lunes = {
            R.id.botonVideoDominadas,
            R.id.botonVideoRemoMancuernas,
            R.id.botonVideoPesoMuertoRumano,
            R.id.buttonVideoJalonPronoPecho,
            R.id.botonVideoFacePull,
            R.id.botonVideoCurlBarra
    };

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_definicion_lunes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_lunes, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el array de URLs desde strings.xml
        String[] urls = getResources().getStringArray(R.array.urls_definicion_lunes);

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (int i = 0; i < button_ids_definicion_lunes.length; i++) {
            Button button = view.findViewById(button_ids_definicion_lunes[i]);
            int finalI = i; // Variable final para ser usada en la lambda
            button.setOnClickListener(v -> YoutubeUtils.openYoutubeVideo(getContext(), urls[finalI]));
        }

        // Array de lista de IDs de las ImageView a las que quieres aplicar la animación
        int[] imageButton_ids_definicion_lunes = {
                R.id.imagenDominadas,
                R.id.imagenRemoMancuernas,
                R.id.imagenPesoMuertoRumano,
                R.id.imageJalonPronoPecho,
                R.id.imagenFacePull,
                R.id.imagenCurlBarra
        };

        // Configurar un listener para cada ImageView. Cuando se hace clic en una ImageView, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageButton_ids_definicion_lunes) {
            ImageButton imageButton = view.findViewById(id);
            imageButton.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                imageButton.startAnimation(animation);

                new Handler().postDelayed(() -> imageButton.clearAnimation(), 3000);
            });
        }
    }
}