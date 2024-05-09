package com.example.tfg.jc;

import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tfg.R;

public class CronometroFragment extends Fragment {

    private Vibrator vibrator;
    private TextView timeRemaining;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cronometro, container, false);

        timeRemaining = view.findViewById(R.id.timeRemaining);

        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
                timeRemaining.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                if (vibrator != null) {
                    vibrator.vibrate(500);
                }
            }
        }.start();

        return view;
    }
}