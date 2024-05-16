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

public class DefinicionMartesFragment extends Fragment {

    // Constructor vacío requerido para instanciar el fragmento
    public DefinicionMartesFragment() {
    }

    // Array de IDs de botones
    private final int[] button_ids_definicion_martes = {
            R.id.botonVideoPressMilitarBarra,
            R.id.botonVideoJalonNeutroPecho,
            R.id.botonVideoPressPlanoMancuernas,
            R.id.botonVideoCrucePoleaBajaUnilateral,
            R.id.botonVideoRemoNeutroPolea,
            R.id.botonVideoLateralesPoleaTorso
    };

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_definicion_martes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_martes, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el array de URLs desde strings.xml
        String[] urls = getResources().getStringArray(R.array.urls_definicion_martes);

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (int i = 0; i < button_ids_definicion_martes.length; i++) {
            Button button = view.findViewById(button_ids_definicion_martes[i]);
            int finalI = i; // Variable final para ser usada en la lambda
            button.setOnClickListener(v -> YoutubeUtils.openYoutubeVideo(getContext(), urls[finalI]));
        }

        // Array de lista de IDs de las ImageView a las que quieres aplicar la animación
        int[] imageButton_ids_definicion_martes = {
                R.id.imagenPressMilitarBarra,
                R.id.imagenJalonNeutroPecho,
                R.id.imagenPressPlanoMancuernas,
                R.id.imageCrucePoleaBajaUnilateral,
                R.id.imagenRemoNeutroPolea,
                R.id.imagenLateralesPoleaTorso
        };

        // Configurar un listener para cada ImageView. Cuando se hace clic en una ImageView, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageButton_ids_definicion_martes) {
            ImageButton imageButton = view.findViewById(id);
            imageButton.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                imageButton.startAnimation(animation);

                new Handler().postDelayed(() -> imageButton.clearAnimation(), 3000);
            });
        }
    }
}