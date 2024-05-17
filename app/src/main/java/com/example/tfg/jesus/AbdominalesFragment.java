package com.example.tfg.jesus;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class AbdominalesFragment extends Fragment {

    // Constructor vacío requerido para instanciar el fragmento
    public AbdominalesFragment() {
    }

    private final int[] button_ids_abdominales = {
            R.id.botonVideoRuedaAbsPie,
            R.id.botonVideoDragonFlag,
            R.id.botonVideoStirThePot,
            R.id.botonVideoGirosRusosBarra
    };

    // Handler para detener la animación
    private final Handler handler = new Handler();

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_volumen_viernes
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_miercoles_abdominales, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Obtener el array de URLs desde strings.xml
        String[] urls = getResources().getStringArray(R.array.urls_abdominales);

        // Configurar los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
        for (int i = 0; i < button_ids_abdominales.length; i++) {
            Button button = view.findViewById(button_ids_abdominales[i]);
            int finalI = i; // Variable final para ser usada en la lambda
            button.setOnClickListener(v -> openYoutubeVideo(urls[finalI]));
        }

        // Array de lista de IDs de las ImageButton a las que quieres aplicar la animación
        int[] imageButton_ids_abdominales = {
                R.id.imagenRuedaAbsPie,
                R.id.imagenDragonFlag,
                R.id.imagenStirThePot,
                R.id.imagenGirosRusosBarra
        };
        // Configurar un listener para cada ImageButton. Cuando se hace clic en una ImageButton, se inicia una animación de rotación que dura 3 segundos. Después de 3 segundos, la animación se detiene
        for (int id : imageButton_ids_abdominales) {
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