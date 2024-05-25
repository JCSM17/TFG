package com.example.tfg.jesus;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    private final int countdownTime = 60; // 60 seconds
    private final long clockTime = countdownTime * 1000;
    private final float progressTime = clockTime / 1000.0f;
    private MediaPlayer mediaPlayer;

    private enum TimerState {
        RESET, PLAY, PAUSE
    }

    private TimerState state = TimerState.RESET;

    private Button actionButton;
    private com.example.tfg.jesus.CustomCountdownTimer customCountdownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cronometro, container, false);

        customCountdownTimer = new CustomCountdownTimer(getContext(), clockTime, 1000);
        customCountdownTimer.setOnTick(millisUntilFinished -> {
            int secondsLeft = (int) (millisUntilFinished / 1000);
            timerFormat(secondsLeft, timeTxt);
        });

        actionButton = view.findViewById(R.id.actionBtn);
        actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_play, 0, 0, 0);
        actionButton.setOnClickListener(v -> {
            switch (state) {
                case RESET:
                    circularProgressBar.setProgress((int) progressTime);
                    customCountdownTimer.restartTimer();
                    actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_pause, 0, 0, 0);
                    state = TimerState.PLAY;
                    break;
                case PLAY:
                    customCountdownTimer.pauseTimer();
                    actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_play, 0, 0, 0);
                    state = TimerState.PAUSE;
                    break;
                case PAUSE:
                    customCountdownTimer.resumeTimer();
                    actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_pause, 0, 0, 0);
                    state = TimerState.PLAY;
                    break;
            }
        });

        mediaPlayer = MediaPlayer.create(getActivity(), R.raw.end_timer_sound);

        customCountdownTimer.setOnFinish(new CustomCountdownTimer.OnFinish() {
            @Override
            public void onFinish() {
                timerFormat(0, timeTxt);
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mp -> {
                        mp.release();
                        mediaPlayer = null;
                    });
                }
                actionButton.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icono_play, 0, 0, 0);
                state = TimerState.RESET;
            }

            @Override
            public void onVibrate() {
                if (getActivity() != null) {
                    Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                    if (vibrator != null) {
                        long[] pattern = {0, VIBRATION_DURATION, 500, VIBRATION_DURATION, 500, VIBRATION_DURATION}; // Vibrate 3 times with 500ms pause
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1));
                        } else {
                            vibrator.vibrate(pattern, -1);
                        }
                    }
                }
            }
        });

        timeTxt = view.findViewById(R.id.timeTxt);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);

        circularProgressBar.setMax((int) progressTime);
        circularProgressBar.setProgress((int) progressTime);

        customCountdownTimer.startTimer();

        return view;
    }

    private void timerFormat(int secondsLeft, TextView timeTxt) {
        circularProgressBar.setProgress(secondsLeft);
        String timeFormat1 = decimalFormat.format(secondsLeft);
        timeTxt.setText(timeFormat1);
    }

    @Override
    public void onPause() {
        customCountdownTimer.pauseTimer();
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        customCountdownTimer.resumeTimer();
    }

    @Override
    public void onDestroy() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        customCountdownTimer.destroyTimer();
        super.onDestroy();
    }
}