package com.example.tfg.jesus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
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


public class VolumenLunesFragment extends Fragment {

    // Array de IDs de botones
    private final int[] button_ids_volumen_lunes = {
            R.id.botonVideoRemoMancuernaVolumen,
            R.id.botonVideoJalonUnilateralSentado,
            R.id.botonVideoRemoNeutroPoleaVolumen,
            R.id.buttonVideoCurlBicepsBarraZcott,
            R.id.botonVideoCurlPoleaAlta

    };

    // Handler para detener la animación
    private final Handler handler = new Handler();

    // Constructor vacío requerido para instanciar el fragmento
    public VolumenLunesFragment() {
    }

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_volumen_jueves
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_lunes, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el array de URLs desde strings.xml
        String[] urls = getResources().getStringArray(R.array.urls_volumen_lunes);

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (int i = 0; i < button_ids_volumen_lunes.length; i++) {
            Button button = view.findViewById(button_ids_volumen_lunes[i]);
            int finalI = i; // Variable final para ser usada en la lambda
            button.setOnClickListener(v -> openYoutubeVideo(urls[finalI]));
        }

        // Array de lista de IDs de las ImageView a las que quieres aplicar la animación
        int[] imageButton_ids_volumen_jueves = {
                R.id.imagenPBP,
                R.id.imagenFondos,
                R.id.imagenCruces,
                R.id.imagePressFrances,
                R.id.imagenExtTriceps,
                R.id.imagenPalof
        };

        // Configurar un listener para cada ImageButton. Cuando se hace clic en un ImageButton, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageButton_ids_volumen_jueves) {
            ImageButton imageButton = view.findViewById(id);
            imageButton.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                imageButton.startAnimation(animation);

                handler.postDelayed(() -> imageButton.clearAnimation(), 3000);
            });
        }
    }

    // Este método se utiliza para abrir un video de YouTube en la app y sino en el navegador. Se crea un Intent con la acción Intent.ACTION_VIEW y la URL del video de YouTube, y luego se inicia este Intent
    private void openYoutubeVideo(String url) {
        Intent intentApp = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intentApp.setPackage("com.google.android.youtube");

        Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            // Intenta abrir la aplicación de YouTube
            startActivity(intentApp);
        } catch (ActivityNotFoundException e) {
            // Si la aplicación de YouTube no está instalada, abre el video en el navegador
            startActivity(intentBrowser);
        }
    }
}