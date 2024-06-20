package com.example.tfg.jesus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

import java.text.DecimalFormat;

public class CountdownFragment extends Fragment {

    private static final int VIBRATION_DURATION = 500;
    private static final String TIMER_FORMAT = "00";
    private static final DecimalFormat decimalFormat = new DecimalFormat(TIMER_FORMAT);

    private TextView timeTxt;
    private ProgressBar circularProgressBar;

    // Tiempo de cuenta regresiva en segundos
    private final int countdownTime = 60; // 60 seconds
    private final long clockTime = countdownTime * 1000;
    private final float progressTime = clockTime / 1000.0f;
    private MediaPlayer mediaPlayer;
    private BroadcastReceiver br;
    private static final int WAKELOCK_TIMEOUT = 60 * 1000; // 60 seconds
    private PowerManager.WakeLock wakeLock;

    // Estados posibles del temporizador
    private enum TimerState {
        RESET, PLAY, PAUSE
    }

    private TimerState state = TimerState.RESET;
    ImageButton actionButton;
    private com.example.tfg.jesus.CustomCountdownTimer customCountdownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cronometro, container, false);

        // Iniciar el TimerService
        Intent intent = new Intent(getActivity(), TimerService.class);
        getActivity().startService(intent);

        customCountdownTimer = new CustomCountdownTimer(getContext(), clockTime, 1000);

        // Crear el MediaPlayer una sola vez
        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.end_timer_sound);

        customCountdownTimer.setOnTick(millisUntilFinished -> {
            int secondsLeft = (int) (millisUntilFinished / 1000);
            timerFormat(secondsLeft, timeTxt);

            // Reproducir sonido y vibrar cuando quedan 3 segundos
            if (millisUntilFinished <= 4000 && !mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> {
                    // No liberar el MediaPlayer aquí
                });
                onVibrate();
            }
        });

        actionButton = view.findViewById(R.id.actionBtn);
        actionButton.setImageResource(R.drawable.icono_play);
        actionButton.setOnClickListener(v -> {
            AudioManager audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
            if (audioManager != null) {
                // Ajustar el volumen al máximo
                int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0);
            }

            // Controlar el estado del temporizador al hacer clic en el botón
            switch (state) {
                case RESET:
                    circularProgressBar.setProgress((int) progressTime);
                    customCountdownTimer.restartTimer();
                    actionButton.setImageResource(R.drawable.icono_pause);
                    state = TimerState.PLAY;
                    customCountdownTimer.startTimer();
                    startTimer(); // Adquirir WakeLock
                    break;
                case PLAY:
                    customCountdownTimer.pauseTimer();
                    actionButton.setImageResource(R.drawable.icono_play);
                    state = TimerState.PAUSE;
                    stopTimer(); // Liberar WakeLock
                    break;
                case PAUSE:
                    customCountdownTimer.resumeTimer();
                    actionButton.setImageResource(R.drawable.icono_pause);
                    state = TimerState.PLAY;
                    startTimer(); // Adquirir WakeLock
                    break;
            }
        });

        customCountdownTimer.setOnFinish(new CustomCountdownTimer.OnFinish() {
            @Override
            public void onFinish() {
                timerFormat(0, timeTxt);
                actionButton.setImageResource(R.drawable.icono_play);
                state = TimerState.RESET;
            }

            @Override
            public void onVibrate() {
                // Método interno para vibrar el dispositivo
                onVibrate();
            }
        });

        timeTxt = view.findViewById(R.id.timeTxt);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
        circularProgressBar.setMax((int) progressTime);
        circularProgressBar.setProgress((int) progressTime);

        // Establecer el BroadcastReceiver para actualizar el temporizador
        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long millisUntilFinished = intent.getLongExtra("countdown", 0);
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerFormat(secondsLeft, timeTxt);
            }
        };

        // Crear un nuevo WakeLock para mantener el dispositivo activo
        PowerManager powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        if (powerManager != null) {
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyApp::MyWakelockTag");
        }

        return view;
    }

    private void startTimer() {
        // Método para adquirir el WakeLock
        if (wakeLock != null && !wakeLock.isHeld()) {
            wakeLock.acquire(WAKELOCK_TIMEOUT);
        }
    }

    private void stopTimer() {
        // Método para liberar el WakeLock
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }
    }

    private void onVibrate() {
        // Método para vibrar el dispositivo
        if (getActivity() != null) {
            Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
            if (vibrator != null) {
                long[] pattern = {0, VIBRATION_DURATION, 500, VIBRATION_DURATION, 500, VIBRATION_DURATION}; // Vibra 3 veces con pausa de 500 ms
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
                } else {
                    vibrator.vibrate(pattern, -1);
                }
            }
        }
    }

    private void timerFormat(int secondsLeft, TextView timeTxt) {
        // Método para formatear y mostrar el tiempo restante en el temporizador
        circularProgressBar.setProgress(secondsLeft);
        String timeFormat1 = decimalFormat.format(secondsLeft);
        timeTxt.setText(timeFormat1);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(br, new IntentFilter("com.example.tfg.jesus.COUNTDOWN_BR"));
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(br);
    }

    @Override
    public void onDestroy() {
        // Liberar recursos al destruir el Fragment.
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        customCountdownTimer.destroyTimer();

        // Liberar el WakeLock al destruir el Fragment
        if (wakeLock != null && wakeLock.isHeld()) {
            wakeLock.release();
        }

        super.onDestroy();
    }
}