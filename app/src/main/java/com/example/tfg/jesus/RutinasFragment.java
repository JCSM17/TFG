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

import java.util.HashMap;
import java.util.Map;

public class RutinasFragment extends Fragment {

    // Constantes para los nombres de los argumentos que este fragmento puede aceptar
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // Variables de instancia para almacenar los valores de los argumentos
    private String mParam1;
    private String mParam2;

    // URLs de los videos de YouTube
    private static final Map<Integer, String> BUTTON_TO_URL_MAP = new HashMap<Integer, String>() {{
        put(R.id.botonVideoPressBanca, "https://www.youtube.com/shorts/i14IBMNQDQQ");
        put(R.id.botonVideoFondos, "https://www.youtube.com/shorts/lC7lLkjDZ_k");
        put(R.id.botonVideoPolea, "https://www.youtube.com/shorts/wHKYSABOpGY");
        put(R.id.buttonFrancesMancuerna, "https://www.youtube.com/shorts/DFFD1LU_iXw");
        put(R.id.botonVideoExtTriceps, "https://www.youtube.com/shorts/JVc1KAB_HLY");
        put(R.id.botonVideoPressPalof, "https://www.youtube.com/shorts/r3fM413P3W0");
    }};

    // Constructor vacío requerido para instanciar el fragmento
    public RutinasFragment() {
        // Required empty public constructor
    }

    // Método de fábrica estático para crear una nueva instancia de RutinasFragment con los argumentos proporcionados
    public static RutinasFragment newInstance(String param1, String param2) {
        RutinasFragment fragment = new RutinasFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // Este método se llama cuando se crea el fragmento. Aquí se recuperan los argumentos pasados al fragmento y se almacenan en las variables de instancia
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    // Este método se llama para inflar la vista del fragmento. Aquí se infla el layout fragment_volumen_jueves
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_volumen_jueves, container, false);
    }

    // Este método se llama después de que la vista del fragmento se ha creado. Aquí se configuran los listeners de los botones y la animación de la ImageView
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupButtons(view);
        setupImageViewAnimations(view);
        setupCountDownTimer(view);
    }

    // Configura los listeners de los botones para abrir los videos de YouTube correspondientes cuando se hace clic en ellos
    private void setupButtons(View view) {
        for (Map.Entry<Integer, String> entry : BUTTON_TO_URL_MAP.entrySet()) {
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
        new Handler().postDelayed(imageView::clearAnimation, 3000);
    }

    // Configura el temporizador de cuenta regresiva
    private void setupCountDownTimer(View view) {
        TextView questionText = view.findViewById(R.id.textoCronometro);
        TextView timeRemaining = view.findViewById(R.id.timeRemaining);
        questionText.setOnClickListener(v -> startCountDownTimer(timeRemaining));
    }

    private void startCountDownTimer(TextView timeRemaining) {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                timeRemaining.setText(String.valueOf(secondsRemaining));
            }

            public void onFinish() {
                timeRemaining.setText("0");
                vibrateAndPlayBeep();
            }
        }.start();
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


    // Este método se utiliza para abrir un video de YouTube en el navegador. Se crea un Intent con la acción Intent.ACTION_VIEW y la URL del video de YouTube, y luego se inicia este Intent
    private void openYoutubeVideo(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}
