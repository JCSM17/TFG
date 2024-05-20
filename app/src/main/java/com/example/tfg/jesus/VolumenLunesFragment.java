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

    private static final int[] BUTTON_IDS_VOLUMEN_LUNES = {
            R.id.botonVideoRemoMancuernaVolumen,
            R.id.botonVideoJalonUnilateralSentado,
            R.id.botonVideoRemoNeutroPoleaVolumen,
            R.id.buttonVideoCurlBicepsBarraZcott,
            R.id.botonVideoCurlPoleaAlta
    };
    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_LUNES = {
            R.id.imagenRemoMancuernaVolumen,
            R.id.imagenJalonUnilateralSentado,
            R.id.imagenRemoNeutroPoleaVolumen,
            R.id.imagenCurlBicepsBarraZcott,
            R.id.imagenCurlPoleaAlta
    };
    private static final int ANIMATION_DURATION = 3000;

    private final Handler handler = new Handler();

    public VolumenLunesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_lunes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_lunes);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_LUNES.length; i++) {
            setupButton(view, BUTTON_IDS_VOLUMEN_LUNES[i], urls[i]);
        }

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_LUNES) {
            setupImageButton(view, id);
        }
    }

    private void setupButton(View view, int buttonId, String url) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> openYoutubeVideo(url));
    }

    private void setupImageButton(View view, int imageButtonId) {
        ImageButton imageButton = view.findViewById(imageButtonId);
        imageButton.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            imageButton.startAnimation(animation);
            handler.postDelayed(() -> imageButton.clearAnimation(), ANIMATION_DURATION);
        });
    }

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