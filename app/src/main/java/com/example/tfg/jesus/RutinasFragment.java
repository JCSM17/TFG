package com.example.tfg.jesus;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tfg.R;

import java.util.Map;

public class RutinasFragment extends Fragment {

    private Map<Integer, String> buttonToUrlMap;

    // Constructor que acepta el mapa de botones a URL como parámetro
    public RutinasFragment(Map<Integer, String> buttonToUrlMap) {
        this.buttonToUrlMap = buttonToUrlMap;
    }

    // Este método se llama para inflar la vista del fragmento.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflar el layout correspondiente según el ID proporcionado al crear la instancia del fragmento
        int layoutId = getArguments().getInt("layout_id");
        return inflater.inflate(layoutId, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupButtons(view);
        setupImageViewAnimations(view);
    }

    // Configura los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
    private void setupButtons(View view) {
        for (Map.Entry<Integer, String> entry : buttonToUrlMap.entrySet()) {
            Button button = view.findViewById(entry.getKey());
            button.setOnClickListener(v -> openYoutubeVideo(entry.getValue()));
        }
    }

    // Configura la animación de las ImageView
    private void setupImageViewAnimations(View view) {
        int[] imageViewIds = {
                R.id.imagenPBP,
                R.id.imagenFondos,
                R.id.imagenCruces,
                R.id.imagePressFrances,
                R.id.imagenExtTriceps,
                R.id.imagenPalof
        };
        for (int id : imageViewIds) {
            ImageView imageView = view.findViewById(id);
            imageView.setOnClickListener(v -> startRotationAnimation(imageView));
        }
    }

    // Inicia la animación de rotación en la ImageView
    private void startRotationAnimation(ImageView imageView) {
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
        imageView.startAnimation(animation);
    }


    // Vibra el teléfono tres veces y reproduce un sonido de pitido
    private void vibrateAndPlayBeep() {
        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        if (vibrator != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(new long[]{0, 500, 500, 500}, -1));
            } else {
                vibrator.vibrate(new long[]{0, 500, 500, 500}, -1);
            }
        }

        // Reproduce el sonido de pitido
        MediaPlayer mediaPlayer = MediaPlayer.create(getContext(), R.raw.countdown_to_fight);
        mediaPlayer.start();
    }

    // Este método se utiliza para abrir un video de YouTube en el navegador.
    private void openYoutubeVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}