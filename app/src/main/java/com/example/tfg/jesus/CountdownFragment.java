package com.example.tfg.jesus;

import android.os.Bundle;
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
    private TextView timeTxt;
    private ProgressBar circularProgressBar;
    private ProgressBar linearProgressBar;

    private final int countdownTime = 30; // 30 seconds
    private final long clockTime = countdownTime * 1000;
    private final float progressTime = clockTime / 1000.0f;

    private com.example.tfg.jesus.CustomCountdownTimer customCountdownTimer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cronometro, container, false);

        timeTxt = view.findViewById(R.id.timeTxt);
        circularProgressBar = view.findViewById(R.id.circularProgressBar);
      //  linearProgressBar = view.findViewById(R.id.linearProgressBar);

        customCountdownTimer = new CustomCountdownTimer(clockTime, 1000);
        customCountdownTimer.setOnTick(new com.example.tfg.jesus.CustomCountdownTimer.OnTick() {
            @Override
            public void onTick(long millisUntilFinished) {
                int secondsLeft = (int) (millisUntilFinished / 1000);
                timerFormat(secondsLeft, timeTxt);
            }
        });
        customCountdownTimer.setOnFinish(new CustomCountdownTimer.OnFinish() {
            @Override
            public void onFinish() {
                timerFormat(0, timeTxt);
            }
        });

        circularProgressBar.setMax((int) progressTime);
      //  linearProgressBar.setMax((int) progressTime);

        circularProgressBar.setProgress((int) progressTime);
      //  linearProgressBar.setProgress((int) progressTime);

        customCountdownTimer.startTimer();

        Button pauseBtn = view.findViewById(R.id.pauseBtn);
        Button resumeBtn = view.findViewById(R.id.playBtn);
        Button resetBtn = view.findViewById(R.id.resetBtn);

        pauseBtn.setOnClickListener(v -> customCountdownTimer.pauseTimer());
        resumeBtn.setOnClickListener(v -> customCountdownTimer.resumeTimer());
        resetBtn.setOnClickListener(v -> {
            circularProgressBar.setProgress((int) progressTime);
      //      linearProgressBar.setProgress((int) progressTime);
            customCountdownTimer.restartTimer();
        });

        return view;
    }

    private void timerFormat(int secondsLeft, TextView timeTxt) {
    //    linearProgressBar.setProgress(secondsLeft);
        circularProgressBar.setProgress(secondsLeft);
        DecimalFormat decimalFormat = new DecimalFormat("00");
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
        customCountdownTimer.destroyTimer();
        super.onDestroy();
    }
}