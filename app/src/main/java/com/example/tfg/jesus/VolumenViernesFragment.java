package com.example.tfg.jesus;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;
import com.example.tfg.databinding.FragmentVolumenViernesBinding;
import com.example.tfg.jc.MenuActivity;
import com.example.tfg.jc.YoutubeUtils;

public class VolumenViernesFragment extends Fragment {

    private static final int[] BUTTON_IDS_VOLUMEN_VIERNES = {
            R.id.botonVideoDominadasPronas,
            R.id.botonVideoPesoMuerto,
            R.id.botonVideoPressBancaMancuernas,
            R.id.botonVideoElevacionPolea,
            R.id.botonVideoCurlInclinadoMancuernas,
            R.id.botonVideoExtencionTricepsPolea
    };

    private static final int[] IMAGE_BUTTON_IDS_VOLUMEN_VIERNES = {
            R.id.imagenPronas,
            R.id.imagenPesoMuerto,
            R.id.imagenPressBancaMancuernas,
            R.id.imageElevacionPolea,
            R.id.imagenInclinadoMancuernas,
            R.id.imagenExtTricepsPoleaViernes
    };

    private final Handler handler = new Handler();
    private FragmentVolumenViernesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentVolumenViernesBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String[] urls = getResources().getStringArray(R.array.urls_volumen_viernes);

        setupButtons(urls);
        setupImageButtons();
    }

    private void setupButtons(String[] urls) {
        for (int i = 0; i < BUTTON_IDS_VOLUMEN_VIERNES.length; i++) {
            Button button = binding.getRoot().findViewById(BUTTON_IDS_VOLUMEN_VIERNES[i]);
            int finalI = i;
            button.setOnClickListener(v -> YoutubeUtils.openYoutubeVideo(getContext(), urls[finalI]));
        }

        binding.imagenCheckVolumenViernes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MenuActivity.class);
            startActivity(intent);
        });
    }

    private void setupImageButtons() {
        for (int id : IMAGE_BUTTON_IDS_VOLUMEN_VIERNES) {
            ImageButton imageButton = binding.getRoot().findViewById(id);
            imageButton.setOnClickListener(v -> {
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
                imageButton.startAnimation(animation);
                handler.postDelayed(imageButton::clearAnimation, 3000);
            });
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}