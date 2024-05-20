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


public class VolumenJuevesFragment extends Fragment {

    private static final int[] BUTTON_IDS_VOLUMEN_JUEVES = {
            R.id.botonVideoPressBanca,
            R.id.botonVideoFondos,
            R.id.botonVideoPolea,
            R.id.buttonFrancesMancuerna,
            R.id.botonVideoExtTriceps,
            R.id.botonVideoPressPalof
    };
    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_JUEVES = {
            R.id.imagenPBP,
            R.id.imagenFondos,
            R.id.imagenCruces,
            R.id.imagePressFrances,
            R.id.imagenExtTriceps,
            R.id.imagenPalof
    };
    private static final int ANIMATION_DURATION = 3000;

    private final Handler handler = new Handler();

    public VolumenJuevesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_jueves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_jueves);

        for (int i = 0; i < BUTTON_IDS_VOLUMEN_JUEVES.length; i++) {
            setupButton(view, BUTTON_IDS_VOLUMEN_JUEVES[i], urls[i]);
        }

        setupButton(view, R.id.imagenCheckVolumenJueves, MenuActivity.class);

        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_JUEVES) {
            setupImageButton(view, id);
        }
    }

    private void setupButton(View view, int buttonId, String url) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> YoutubeUtils.openYoutubeVideo(getContext(), url));
    }

    private void setupButton(View view, int buttonId, Class<?> activityClass) {
        Button button = view.findViewById(buttonId);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), activityClass);
            startActivity(intent);
        });
    }

    private void setupImageButton(View view, int imageButtonId) {
        ImageButton imageButton = view.findViewById(imageButtonId);
        imageButton.setOnClickListener(v -> {
            Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
            imageButton.startAnimation(animation);
            handler.postDelayed(() -> imageButton.clearAnimation(), ANIMATION_DURATION);
        });
    }
}