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

import java.util.HashMap;
import java.util.Map;

public class DefinicionLunesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_LUNES = {
            R.id.botonVideoDominadas,
            R.id.botonVideoRemoMancuernas,
            R.id.botonVideoPesoMuertoRumano,
            R.id.buttonVideoJalonPronoPecho,
            R.id.botonVideoFacePull,
            R.id.botonVideoCurlBarra
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_LUNES = {
            R.id.imagenDominadas,
            R.id.imagenRemoMancuernas,
            R.id.imagenPesoMuertoRumano,
            R.id.imageJalonPronoPecho,
            R.id.imagenFacePull,
            R.id.imagenCurlBarra
    };
    private static final int ANIMATION_DURATION = 3000;

    private final Handler handler = new Handler();

    public DefinicionLunesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_lunes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_lunes);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_LUNES.length; i++) {
            setupButton(view, BUTTON_IDS_DEFINICION_LUNES[i], urls[i]);
        }

        setupButton(view, R.id.imagenCheckDefinicionLunes, MenuActivity.class);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_LUNES) {
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