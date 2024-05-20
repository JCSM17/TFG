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

public class VolumenMartesFragment extends Fragment {

    private static final int ANIMATION_DURATION = 3000;
    private final int[] buttonIdsVolumenMartes = {
            R.id.botonVideoSentadillaConBarra,
            R.id.botonVideoPressMilitar,
            R.id.botonVideoPrensaInclinadaa,
            R.id.botonVideoElevacionesLateralesMancuerna,
            R.id.botonVideoCurlIsquiosTumbados,
            R.id.botonVideoFacepull
    };

    private final int[] imageButtonIdsVolumenJueves = {
            R.id.imagenPBP,
            R.id.imagenFondos,
            R.id.imagenCruces,
            R.id.imagePressFrances,
            R.id.imagenExtTriceps,
            R.id.imagenPalof
    };

    private final Handler handler = new Handler();

    public VolumenMartesFragment() {
        // Constructor vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_martes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_martes);

        setupButtonListeners(view, urls);
        setupImageButtonListeners(view);
    }

    private void setupButtonListeners(View view, String[] urls) {
        for (int i = 0; i < buttonIdsVolumenMartes.length; i++) {
            Button button = view.findViewById(buttonIdsVolumenMartes[i]);
            int index = i;
            button.setOnClickListener(v -> openYoutubeVideo(urls[index]));
        }
    }

    private void setupImageButtonListeners(View view) {
        for (int id : imageButtonIdsVolumenJueves) {
            ImageButton imageButton = view.findViewById(id);
            imageButton.setOnClickListener(v -> startRotationAnimation(imageButton));
        }
    }

    private void startRotationAnimation(ImageButton imageButton) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        imageButton.startAnimation(animation);

        handler.postDelayed(imageButton::clearAnimation, ANIMATION_DURATION);
    }

    private void openYoutubeVideo(String url) {
        Intent intentApp = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intentApp.setPackage("com.google.android.youtube");

        Intent intentBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));

        try {
            startActivity(intentApp);
        } catch (ActivityNotFoundException e) {
            startActivity(intentBrowser);
        }
    }
}