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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.databinding.FragmentVolumenMartesBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VolumenMartesFragment extends Fragment {

    private static final int ANIMATION_DURATION = 3000;
    private static final String YOUTUBE_PACKAGE = "com.google.android.youtube";
    private FragmentVolumenMartesBinding binding;
    private final Handler handler = new Handler();

    private final Map<Integer, Integer> buttonImageMap = new HashMap<>();
    private String[] urls;

    public VolumenMartesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVolumenMartesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    private Animation animation;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        urls = getResources().getStringArray(R.array.urls_volumen_martes);

        initializeButtonImageMap();
        setupListeners();

        // Carga la animación una vez aquí
        animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
    }
    private void initializeButtonImageMap() {
        buttonImageMap.put(R.id.botonVideoSentadillaConBarra, R.id.imagenSentadillaConBarra);
        buttonImageMap.put(R.id.botonVideoPressMilitar, R.id.imagenPressMilitar);
        buttonImageMap.put(R.id.botonVideoPrensaInclinadaa, R.id.imagenPrensaInclinadaa);
        buttonImageMap.put(R.id.botonVideoElevacionesLateralesMancuerna, R.id.imagenElevacionesLateralesMancuerna);
        buttonImageMap.put(R.id.botonVideoCurlIsquiosTumbados, R.id.imagenCurlIsquiosTumbados);
        buttonImageMap.put(R.id.botonVideoFacepull, R.id.imagenFacepull);
    }

    private void setupListeners() {
        int index = 0;
        for (Map.Entry<Integer, Integer> entry : buttonImageMap.entrySet()) {
            Button button = binding.getRoot().findViewById(entry.getKey());
            ImageButton imageButton = binding.getRoot().findViewById(entry.getValue());

            int finalIndex = index;
            button.setOnClickListener(v -> openYoutubeVideo(urls[finalIndex]));
            imageButton.setOnClickListener(v -> startRotationAnimation(imageButton));
            index++;
        }
    }

    private void startRotationAnimation(ImageButton imageButton) {
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // No se necesita hacer nada aquí
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                imageButton.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // No se necesita hacer nada aquí
            }
        });

        imageButton.startAnimation(animation);
    }

    private void openYoutubeVideo(String url) {
        Intent intentApp = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intentApp.setPackage(YOUTUBE_PACKAGE);

        Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            startActivity(intentApp);
        } catch (ActivityNotFoundException e) {
            // Muestra un mensaje de error al usuario
            Toast.makeText(getContext(), "No se encontró la aplicación de YouTube. Abriendo en el navegador...", Toast.LENGTH_LONG).show();
            startActivity(intentBrowser);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
