package com.example.tfg.jesus;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

import java.util.HashMap;
import java.util.Map;

public class RutinasFragmentViernes extends Fragment {

    // Constantes para los nombres de los argumentos que este fragmento puede aceptar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables de instancia para almacenar los valores de los argumentos
    private String mParam1;
    private String mParam2;

    // Constructor vacío requerido para instanciar el fragmento
    public RutinasFragmentViernes() {
        // Required empty public constructor
    }

    // Método de fábrica estático para crear una nueva instancia de RutinasFragment con los argumentos proporcionados
    public static RutinasFragment newInstance(String param1, String param2) {
        RutinasFragment fragment = new RutinasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Este método se llama cuando se crea el fragmento. Aquí se recuperan los argumentos pasados al fragmento y se almacenan en las variables de instancia
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_volumen_jueves
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_viernes, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Crear un mapa de IDs de botones a URLs de YouTube
        Map<Integer, String> buttonToUrlMap = new HashMap<>();
        buttonToUrlMap.put(R.id.botonVideoDominadasPronas, "https://www.youtube.com/shorts/w9QEAH6ANt8");
        buttonToUrlMap.put(R.id.botonVideoPesoMuerto, "https://www.youtube.com/shorts/3EhkrUEEPOg");
        buttonToUrlMap.put(R.id.botonVideoPressBancaMancuernas, "https://www.youtube.com/shorts/48L0oQApm_0");
        buttonToUrlMap.put(R.id.buttonVideoElevacionPolea, "https://www.youtube.com/shorts/AqB70P2Yt4w");
        buttonToUrlMap.put(R.id.botonVideoCurlInclinadoMancuernas, "https://www.youtube.com/shorts/jktjmrPmOrs");
        buttonToUrlMap.put(R.id.botonVideoExtencionTricepsPolea, "https://www.youtube.com/shorts/EWsq_UbGqPY");

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (Map.Entry<Integer, String> entry : buttonToUrlMap.entrySet()) {
            Button button = view.findViewById(entry.getKey());
            button.setOnClickListener(v -> openYoutubeVideo(entry.getValue()));
        }

        // Array de lista de IDs de las ImageView a las que quieres aplicar la animación
        int[] imageViewIds = {
                R.id.imagenPBP,
                R.id.imagenFondos,
                R.id.imagenCruces,
                R.id.imagePressFrances,
                R.id.imagenExtTriceps,
                R.id.imagenPalof
        };
        // Configurar un listener para cada ImageView. Cuando se hace clic en una ImageView, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageViewIds) {
            ImageView imageView = view.findViewById(id);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                    imageView.startAnimation(animation);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imageView.clearAnimation();
                        }
                    }, 3000);
                }
            });
        }
    }

    // Este método se utiliza para abrir un video de YouTube en el navegador. Se crea un Intent con la acción Intent.ACTION_VIEW y la URL del video de YouTube, y luego se inicia este Intent
    private void openYoutubeVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}