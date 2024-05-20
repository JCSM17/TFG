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

public class DefinicionJuevesFragment extends Fragment {

    private static final int[] BUTTON_IDS_DEFINICION_JUEVES = {
            R.id.botonVideoPressBancaPlano,
            R.id.botonVideoSentadilla,
            R.id.botonVideoPressmilitarmancuerna,
            R.id.buttonVideoPressInclinadoMancuerna,
            R.id.botonVideoCrucePoleaBaja,
            R.id.botonVideoelevacionlateral
    };
    private static final int[] IMAGE_BUTTON_IDS_DEFINICION_JUEVES = {
            R.id.imagenPressBancaPlano,
            R.id.imagenSentadilla,
            R.id.imagenPressMilitarMancuerna,
            R.id.imagenPressInclinadoMancuerna,
            R.id.imagenCrucePoleaBaja,
            R.id.imagenElevacionLateral,
    };
    private static final int ANIMATION_DURATION = 3000;

    private final Handler handler = new Handler();

    public DefinicionJuevesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_definicion_jueves, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_definicion_jueves);

        for (int i = 0; i < BUTTON_IDS_DEFINICION_JUEVES.length; i++) {
            setupButton(view, BUTTON_IDS_DEFINICION_JUEVES[i], urls[i]);
        }

        setupButton(view, R.id.imagenCheckDefinicionJueves, MenuActivity.class);

        for (int id : IMAGE_BUTTON_IDS_DEFINICION_JUEVES) {
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